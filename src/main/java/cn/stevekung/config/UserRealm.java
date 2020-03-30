package cn.stevekung.config;

import cn.stevekung.pojo.User;
import cn.stevekung.pojo.UserMap;
import cn.stevekung.service.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserServiceImpl userService;

    // 授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("进入授权方法");
        String userName = (String) principalCollection.getPrimaryPrincipal();
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        List<UserMap> mapByUsername = userService.getMapByUsername(userName);

        if (mapByUsername != null){
            for (UserMap userMap : mapByUsername) {
                roles.add(userMap.getRoleName());
                permissions.add(userMap.getPermissionName());
            }

            //组装返回数据
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            simpleAuthorizationInfo.addRoles(roles);
            simpleAuthorizationInfo.addStringPermissions(permissions);
            return simpleAuthorizationInfo;
        } else{
            return null;
        }
    }

    // 认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("进入认证方法");
        //通过token获取用户账号
        String userName = (String) authenticationToken.getPrincipal();
        //通过用户名称查询数据库.那么就会出现用户存在和不存在,
        // 密码正确和不正确四种情况
        String passWord = null;
        User user = userService.getUserByUsername(userName);
        if (user != null){
            passWord = user.getPassword();
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userName, passWord, "UserRealm");

            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            session.setAttribute("loginUser", user);

            return simpleAuthenticationInfo;
        } else {
            return null;
        }
    }
}
