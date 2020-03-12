package com.awakeyo.community.config;


import com.awakeyo.community.pojo.User;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
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
        bean.setUnauthorizedUrl("/toerror");
        bean.setLoginUrl("/tologin");
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
        filterMap.put("/writeBlog","roles[admin]");
        filterMap.put("/editBlog","roles[admin]");
        filterMap.put("/publishBlog","roles[admin]");
        //授权
        filterMap.put("/**","anon");
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
//    @Bean("hashedCredentialsMatcher")
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
//        hashedCredentialsMatcher.setHashIterations(2);// 散列的次数，比如散列两次，相当于md5(md5(""));
//        //hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);// 表示是否存储散列后的密码为16进制，需要和生成密码时的一样，默认是base64；
//        return hashedCredentialsMatcher;
//    }

    @Bean
    public  UserRealm userRealm(){
        UserRealm userRealm=new UserRealm();
//        userRealm.setCredentialsMatcher(matcher);
//        userRealm.setAuthorizationCachingEnabled(true);
        return userRealm;
    }
}
