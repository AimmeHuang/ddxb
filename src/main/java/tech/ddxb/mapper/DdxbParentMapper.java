package tech.ddxb.mapper;

import org.apache.ibatis.annotations.Mapper;
import tech.ddxb.model.DdxbParent;
import tech.ddxb.model.DdxbStudent;

import java.util.List;
import java.util.Map;

/**
 * Created by leahhuang on 2018/7/11.
 */
@Mapper
public interface DdxbParentMapper {

    void saveParent(DdxbParent parent);

    void updateParent(DdxbParent parent);
}
