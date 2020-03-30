package cn.stevekung.controller;

import cn.stevekung.pojo.User;
import cn.stevekung.pojo.UserMap;
import cn.stevekung.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @RequestMapping("/getUserByUsername")
    User getUserByUsername(String userName){
        return userService.getUserByUsername(userName);
    }
    @RequestMapping("/getMapByUsername")
    List<UserMap> getMapByUsername(String userName){
        return userService.getMapByUsername(userName);
    }
}
