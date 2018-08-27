package tech.ddxb.controller.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.ddxb.controller.BaseController;
import tech.ddxb.controller.test.AuthOpt;
import tech.ddxb.controller.test.AuthType;
import tech.ddxb.model.DdxbGrade;
import tech.ddxb.model.PageBean;
import tech.ddxb.model.StudentBean;
import tech.ddxb.service.DdxbTeacherService;
import tech.ddxb.utils.RestResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/ddxb/teacher")
public class DdxbTeacherController extends BaseController {
    @Autowired
    private DdxbTeacherService ddxbTeacherService;

    /**
     * 查询教师
     *
     * @param params
     * @param req
     * @return
     */
    @RequestMapping(value = "queryTeacher", produces = "application/json;charset=UTF-8")
//    @AuthOpt(AuthType.PARENT)
    public RestResponse queryTeacher(@RequestParam Map<String, Object> params, HttpServletRequest req) {
        try {
            return this.getSuccessResponse(ddxbTeacherService.queryDdxbTeacher(params));
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("查询失败");
        }

    }

    /**
     * 学生成绩统计数据展示
     *
     * @return
     */
    @RequestMapping(value = "showGradeInfo", produces = "application/json;charset=UTF-8")
    public RestResponse showGradeInfo(@RequestParam Map<String, Object> params) {
        try {
            Object resultMap = ddxbTeacherService.showGradeInfo(params);
            return this.getSuccessResponse(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("查询失败" + e.getMessage());
        }
    }

    /**
     * 老师填写学生成绩
     *
     * @return
     */
    @RequestMapping(value = "fillInGradeForReport", produces = "application/json;charset=UTF-8")
    public RestResponse fillInGradeForReport(DdxbGrade ddxbGrade) {
        try {
            ddxbTeacherService.fillInGradeForReport(ddxbGrade);
            return this.getSuccessResponse("填写报告成功");
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("填写报告失败" + e.getMessage());
        }
    }

    /**
     * 老师查询学生
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "queryStudent", produces = "application/json;charset=UTF-8")
    public RestResponse queryStudent(@RequestParam Map<String, Object> params) {
        try {
            Object studentList = ddxbTeacherService.queryDdxbStudent(params);
            return this.getSuccessResponse(studentList);
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("查询失败");
        }
    }

    /**
     * 老师添加学生信息
     *
     * @param studentBean
     * @return
     */
    @RequestMapping(value = "saveStudent", produces = "application/json;charset=UTF-8")
    public RestResponse saveStudent(StudentBean studentBean) {
        try {
            ddxbTeacherService.saveStudent(studentBean);
            return this.getSuccessResponse("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("保存失败");
        }
    }

    /**
     * 老师删除学生
     * @param stuId
     * @return
     */
    @RequestMapping(value = "deleteStudent", produces = "application/json;charset=UTF-8")
    public RestResponse deleteStudent(Long stuId){
        try {
            ddxbTeacherService.deleteStudent(stuId);
            return this.getSuccessResponse("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("删除失败");
        }
    }

}
