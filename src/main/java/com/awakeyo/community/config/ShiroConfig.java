package com.awakeyo.community.config;


import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author awakeyoyoyo
 * @className ShiroConfig
 * @description TODO
 * @date 2020-03-11 14:51
 */
@Configuration
public class ShiroConfig {
    //shiroFilterFactoryBean：3
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro的内置过滤器
        /*
        anon:无需认真即可访问
        authc：必须认证了才可以访问
        user：必须拥有记住我功能才能访问
        perms：拥有对某个资源的权限才可以访问
        role：拥有某个角色权限才可以访问
        */
        Map<String, String> filterMap = new LinkedHashMap();
        //授权
//        filterMap.put("/**","perms[user:add]");
        bean.setFilterChainDefinitionMap(filterMap);
        return bean;
    }

    //DefaultWebSecurityManager：2
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);

        return securityManager;
    }


    //创建realm对象，需要自定义类：1
    @Bean
    public  UserRealm userRealm(){
        return new UserRealm();
    }
}
