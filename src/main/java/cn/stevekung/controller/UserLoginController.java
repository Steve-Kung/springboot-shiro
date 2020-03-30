package cn.stevekung.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
//@RequestMapping("/shiro")
public class UserLoginController {
    @GetMapping({"/","/index"})
    public String index(){
        return "index";
    }

    @RequiresPermissions("user:create")
    @RequestMapping("/user/add")
    public String add(){
        return "user/add";
    }

    @RequiresPermissions("user:update")
    @RequestMapping("/user/update")
    public String update(){
        return "user/update";
    }


    @GetMapping("/toLogin")
    public String toLoginPage(){
        return "login";
    }

    @PostMapping("/home")
    public String userLogin(@RequestParam("username") String userName,
                            @RequestParam("password") String passWord,
                            Model model){
        Map<String, Object> resultMap = new HashMap<>();

        UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
        Subject subject = SecurityUtils.getSubject();

        model.addAttribute("resultMap", resultMap);
        //用户登录操作.
        try{
            subject.login(token);
            resultMap.put("code","200");
            resultMap.put("msg","用户登录成功");
            return "index";
        }
        catch (UnknownAccountException e){
            resultMap.put("code","-1");
            resultMap.put("msg","用户名不存在");
            return "login";
        }
        catch (IncorrectCredentialsException e){
            resultMap.put("code","-1");
            resultMap.put("msg","密码错误");
            return "login";
        }
    }

    @RequestMapping("/noauth")
    @ResponseBody
    public String noauth(){
        return "未授权,不允许访问该页面";
    }

    @RequestMapping("/logout")
    public String logout(Model model){
        Map<String, Object> resultMap = new HashMap<>();
        model.addAttribute("resultMap", resultMap);
        resultMap.put("code","200");
        resultMap.put("msg", "用户登出，再见");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "index";
    }
}
