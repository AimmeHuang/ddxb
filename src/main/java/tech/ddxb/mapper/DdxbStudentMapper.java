package tech.ddxb.mapper;

import org.apache.ibatis.annotations.Mapper;
import tech.ddxb.model.DdxbStudent;

import java.util.List;
import java.util.Map;

/**
 * Created by leahhuang on 2018/7/11.
 */
@Mapper
public interface DdxbStudentMapper {
    List<Map<String, Object>> queryStudent(Map<String, Object> params);

    void saveStudent(DdxbStudent student);

    void updateStudent(DdxbStudent student);

    DdxbStudent getDdxbStudentById(Long id);

    void deleteStudent(Long stuId);
}
