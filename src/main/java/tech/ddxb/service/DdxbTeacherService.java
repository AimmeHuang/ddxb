package tech.ddxb.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ddxb.mapper.DdxbGradeMapper;
import tech.ddxb.mapper.DdxbStudentMapper;
import tech.ddxb.mapper.DdxbTeacherMapper;
import tech.ddxb.model.*;
import tech.ddxb.utils.KeyWorker;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

@Service
public class DdxbTeacherService {

    @Autowired
    private DdxbTeacherMapper ddxbTeacherDao;
    @Autowired
    private DdxbStudentMapper ddxbStudentMapper;
    @Autowired
    private DdxbGradeMapper ddxbGradeMapper;


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
     * @param studentBean
     */
    public void saveStudent(StudentBean studentBean) {
        if (null != studentBean.getStuId()) {
            //修改
            ddxbStudentMapper.updateStudent(studentBean);
        } else {
            //添加
            ddxbStudentMapper.saveStudent(studentBean);
        }

    }
}
