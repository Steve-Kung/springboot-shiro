package cn.stevekung.mapper;

import cn.stevekung.pojo.User;
import cn.stevekung.pojo.UserMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    @Select("select * from t_user where username = #{userName}")
    User getUserByUsername(String userName);

    List<UserMap> getMapByUsername(String userName);
}
