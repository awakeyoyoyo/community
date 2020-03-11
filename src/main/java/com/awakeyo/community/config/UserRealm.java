package com.awakeyo.community.config;

import com.awakeyo.community.mapper.RoleMapper;
import com.awakeyo.community.mapper.UserMapper;
import com.awakeyo.community.pojo.Role;
import com.awakeyo.community.pojo.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author awakeyoyoyo
 * @className UserRealm
 * @description TODO
 * @date 2020-03-11 15:58
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了doGetAuthorizationInfo-----授权");
        return null;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
       System.out.println("执行了doGetAuthenticationInfo 认真");
       UsernamePasswordToken userToken=(UsernamePasswordToken)authenticationToken;
       User user=userMapper.selectUserByaccoun_id(userToken.getUsername());
       Set<String> roles=new HashSet<String>();
       List<String> rolesByUserName=roleMapper.getRolesByUserId(user.getId());
       if (user==null){
           return null;
       }
       //可以加密

       return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
    }
}
