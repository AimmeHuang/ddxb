package tech.ddxb.mapper;

import org.apache.ibatis.annotations.Mapper;
import tech.ddxb.model.DdxbStudent;
import tech.ddxb.model.StudentBean;

import java.util.List;
import java.util.Map;

/**
 * Created by leahhuang on 2018/7/11.
 */
@Mapper
public interface DdxbStudentMapper {
    List<Map<String, Object>> queryStudent(Map<String, Object> params);

    void saveStudent(StudentBean student);

    void updateStudent(StudentBean studentBean);
}
