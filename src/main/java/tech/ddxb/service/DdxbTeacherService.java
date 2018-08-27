package tech.ddxb.service;

import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.ddxb.mapper.DdxbGradeMapper;
import tech.ddxb.mapper.DdxbParentMapper;
import tech.ddxb.mapper.DdxbStudentMapper;
import tech.ddxb.mapper.DdxbTeacherMapper;
import tech.ddxb.model.*;
import tech.ddxb.utils.KeyWorker;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DdxbTeacherService {

    @Autowired
    private DdxbTeacherMapper ddxbTeacherDao;
    @Autowired
    private DdxbStudentMapper ddxbStudentMapper;
    @Autowired
    private DdxbGradeMapper ddxbGradeMapper;
    @Autowired
    private DdxbParentMapper ddxbParentMapper;


    /**
     * @Title:
     * @Description: TODO 根据条件查询全部教师
     * @param:
     * @return:
     * @throw:
     * @author:黄雪平
     * @creat_date: 2018/6/4 14:26
     **/
    public Object queryDdxbTeacher(Map<String, Object> params) {
        try {
            Integer page = 1;
            Integer limit = 10;
            if (params.get("page") != null) {
                page = Integer.valueOf(params.get("page").toString());
            }
            if (params.get("page") != null) {
                limit = Integer.valueOf(params.get("limit").toString());
            }

            PageHelper.startPage(page, limit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PageBean<>(ddxbTeacherDao.queryTeacher(params));

    }


    /**
     * @Title:
     * @Description: TODO 保存教师
     * @param:
     * @return:
     * @throw:
     * @author:黄雪平
     * @creat_date: 2018/6/21 19:33
     **/
    public void saveTeacher(DdxbTeacher teacher) {
        ddxbTeacherDao.saveTeacher(teacher);
    }

    /**
     * 学生成绩统计数据展示
     *
     * @param params
     * @return
     */
    public Object showGradeInfo(Map<String, Object> params) {
        //科目展示（固定语数外，暂时不从后台取值）
        //参数中包含老师id和科目名称，查询该老师下的所有学生对应科目的成绩
        Integer page = 1;
        Integer limit = 10;
        if (params.get("page") != null) {
            page = Integer.valueOf(params.get("page").toString());
        }
        if (params.get("page") != null) {
            limit = Integer.valueOf(params.get("limit").toString());
        }
        PageHelper.startPage(page, limit);
        return new PageBean<>(ddxbGradeMapper.queryGradeByParams(params));

    }

    /**
     * 老师填写报告
     *
     * @param ddxbGrade
     */
    public void fillInGradeForReport(DdxbGrade ddxbGrade) {
        if (null == ddxbGrade.getId()) {
            ddxbGrade.setId(KeyWorker.nextId());
            Integer questionTotal = ddxbGrade.getQuestionTotal();
            Integer questionUnfinshed = ddxbGrade.getQuestionUnfinshed();
            Integer questionError = ddxbGrade.getQuestionError();
            // 创建一个数值格式化对象
            NumberFormat numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后2位
            numberFormat.setMaximumFractionDigits(2);
            String correctRate = "";
            String finishedRate = "";
            if (null != questionTotal && questionTotal != 0 && null != questionUnfinshed && null != questionError) {

                correctRate = numberFormat.format((float) (questionTotal - questionError) / (float) questionTotal * 100);
                finishedRate = numberFormat.format((float) (questionTotal - questionUnfinshed) / (float) questionTotal * 100);
            }
            ddxbGrade.setFinishedRate(finishedRate + "%");
            ddxbGrade.setCorrectRate(correctRate + "%");
            ddxbGradeMapper.saveGrade(ddxbGrade);
        }

    }

    /**
     * 教师查看学生信息
     *
     * @param params
     * @return
     */
    public Object queryDdxbStudent(Map<String, Object> params) {
        Integer page = 1;
        Integer limit = 10;
        if (params.get("page") != null) {
            page = Integer.valueOf(params.get("page").toString());
        }
        if (params.get("limit") != null) {
            limit = Integer.valueOf(params.get("limit").toString());
        }

        PageHelper.startPage(page, limit);
        List<Map<String, Object>> students = ddxbStudentMapper.queryStudent(params);
        return new PageBean<>(students);
    }

    /**
     * 添加学生信息
     *
     * @param portalStudent
     */
    @Transactional
    public void saveStudent(StudentBean portalStudent) {
        DdxbStudent student = new DdxbStudent();
        if (null != portalStudent.getStuId()) {
            //修改学生信息
            student = ddxbStudentMapper.getDdxbStudentById(portalStudent.getStuId());
            student.setTeacherId(portalStudent.getTeacherId());
            student.setStuName(portalStudent.getStuName());
            student.setFatherPhone(portalStudent.getFatherPhone());
            student.setMotherPhone(portalStudent.getMotherPhone());
            ddxbStudentMapper.updateStudent(student);
            //保存家长电话信息
            updateParentByStuId(student);

        } else {
            //添加
            student.setId(KeyWorker.nextId());
            student.setTeacherId(portalStudent.getTeacherId());
            student.setStuName(portalStudent.getStuName());
            student.setFatherPhone(portalStudent.getFatherPhone());
            student.setMotherPhone(portalStudent.getMotherPhone());
            ddxbStudentMapper.saveStudent(student);
            //保存家长电话信息
            updateParentByStuId(student);
        }

    }

    /**
     * 老师修改学生家长信息
     * @param student
     */
    public void updateParentByStuId(DdxbStudent student){
        List<DdxbParent> parentList = ddxbParentMapper.getDdxbParentByStuId(student.getId());
        //家长跟学生已绑定关系
        if (null != parentList && parentList.size() > 0) {
            for (int i = 0; i < parentList.size(); i++) {//建立关联
                DdxbParent parent = parentList.get(i);
                if ("M".equals(parent.getGender())) {
                    parent.setParentName(student.getFatherPhone());
                }
                if ("F".equals(parent.getGender())) {
                    parent.setParentName(student.getMotherPhone());
                }
                //（只会修改学生家长信息）
                if (null != parent.getId()) {
                    ddxbParentMapper.updateParent(parent);
                }
            }
        } else {//家长跟学生未绑定关系
            DdxbParent parent = new DdxbParent();//这里的家长信息不应该只是一个空对象
            parent.setStuId(student.getId());

            /*
            目前的情况是，假装家长信息不存在，
            因为家长跟学生未绑定关系，
            缺少一个契机，将家长和学生在后台绑定
             */
            //只能是添加parent信息
            if (StringUtils.isNotBlank(student.getFatherPhone()) || StringUtils.isNotBlank(student.getMotherPhone())) {
                if (null == parent.getId()) {
                    parent.setId(KeyWorker.nextId());
                    if (StringUtils.isNotBlank(student.getFatherPhone())) {
                        parent.setGender("M");
                        parent.setPhoneNumber(student.getFatherPhone());
                    }
                    if (StringUtils.isNotBlank(student.getMotherPhone())) {
                        parent.setGender("F");
                        parent.setPhoneNumber(student.getMotherPhone());
                    }
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String nowStr = now .format(format);

                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date date = sdf.parse(nowStr);
                        parent.setUpdateTime(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    ddxbParentMapper.saveParent(parent);
                }
            }
            if (StringUtils.isNotBlank(student.getFatherPhone()) && StringUtils.isNotBlank(student.getMotherPhone())) {
                if (null == parent.getId()) {
                    parent.setId(KeyWorker.nextId());
                    if (StringUtils.isNotBlank(student.getFatherPhone())) {
                        parent.setGender("M");
                        parent.setPhoneNumber(student.getFatherPhone());
                    }
                    ddxbParentMapper.saveParent(parent);
                }
                if (null == parent.getId()) {
                    parent.setId(KeyWorker.nextId());
                    if (StringUtils.isNotBlank(student.getMotherPhone())) {
                        parent.setGender("F");
                        parent.setPhoneNumber(student.getMotherPhone());
                    }
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String nowStr = now .format(format);

                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date date = sdf.parse(nowStr);
                        parent.setUpdateTime(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    ddxbParentMapper.saveParent(parent);
                }
            }
        }
    }

    /**
     * 根据student的状态，保存parent
     * @param student
     */
    public void saveParent(DdxbStudent student, DdxbParent parent){
        if ("M".equals(parent.getGender())) {
            parent.setParentName(student.getFatherPhone());
        }
        if ("F".equals(parent.getGender())) {
            parent.setParentName(student.getMotherPhone());
        }

        if (null != parent.getId()) {
            ddxbParentMapper.updateParent(parent);
        } else {
            parent.setId(KeyWorker.nextId());
            ddxbParentMapper.saveParent(parent);
        }
    }
}
