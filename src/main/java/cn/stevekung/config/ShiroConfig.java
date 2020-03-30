package cn.stevekung.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    // 1. 第一步：创建realm对象，需要根据数据库自定义UserRealm
    @Bean(name = "userRealm")
    public UserRealm userRealm(){
        UserRealm userRealm = new UserRealm();
        return userRealm;
    }

    // 2. 配置核心安全事务管理器
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm);
        return manager;
    }


    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(defaultWebSecurityManager);

        //配置访问权限
        //注意过滤器配置顺序 不能颠倒
        //anon. 配置不会被拦截的请求 顺序判断
        Map<String, String> filterMap = new LinkedHashMap<>();
        //authc. 配置拦截的请求
        filterMap.put("/user/*","authc");
        // 需要相应权限

        bean.setFilterChainDefinitionMap(filterMap);

        //配置登录的url和登录成功的url以及验证失败的url
        // 设置登录的请求 登陆拦截
        bean.setLoginUrl("/toLogin");
        bean.setSuccessUrl("/index");
        // 未授权页面
        bean.setUnauthorizedUrl("/noauth"); // 不起作用?

        return bean;
    }

    // 整合shiroDialect: 用来整合shiro thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

}
