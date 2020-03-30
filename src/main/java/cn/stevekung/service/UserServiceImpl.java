package cn.stevekung.service;

import cn.stevekung.mapper.UserMapper;
import cn.stevekung.pojo.User;
import cn.stevekung.pojo.UserMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "userCache")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    @Cacheable(key = "'user['+ #result.id +']'")
    public User getUserByUsername(String userName) {
        return userMapper.getUserByUsername(userName);
    }

    @Override
    @Cacheable(key = "'getMapByUsername' + #root.args[0]")
    public List<UserMap> getMapByUsername(String userName) {
        return userMapper.getMapByUsername(userName);
    }
}
