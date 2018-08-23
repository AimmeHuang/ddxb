package tech.ddxb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tech.ddxb.model.DdxbGrade;
import tech.ddxb.model.DdxbTeacher;

import java.util.List;
import java.util.Map;

/**
 * Created by leahhuang on 2018/7/19.
 */
@Mapper
public interface DdxbTeacherMapper {
    List<Map<String, Object>> queryTeacher(Map<String, Object> params);

    void saveTeacher(@Param("teacher") DdxbTeacher teacher);

    void updateTeacher(@Param("teacher") DdxbTeacher teacher);

}
