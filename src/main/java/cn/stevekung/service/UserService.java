package cn.stevekung.service;

import cn.stevekung.pojo.User;
import cn.stevekung.pojo.UserMap;

import java.util.List;

public interface UserService {
    User getUserByUsername(String userName);
    List<UserMap> getMapByUsername(String userName);
}
