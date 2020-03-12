package com.awakeyo.community.config;

import com.awakeyo.community.mapper.PermissionMapper;
import com.awakeyo.community.mapper.RoleMapper;
import com.awakeyo.community.mapper.UserMapper;
import com.awakeyo.community.pojo.Permission;
import com.awakeyo.community.pojo.Role;
import com.awakeyo.community.pojo.User;
import com.awakeyo.community.util.ShiroMd5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
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
    @Autowired
    private PermissionMapper permissionMapper;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了doGetAuthorizationInfo-----授权");
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        User user=(User) SecurityUtils.getSubject().getPrincipal();
        Set<String> roles=new HashSet<String>();
        List<String> rolesByUserId=roleMapper.getRolesByUserId(user.getId());
        for(String role:rolesByUserId) {
            roles.add(role);
        }
        List<String> permissionsByUserName = permissionMapper.getPermissionsByUserId(user.getId());
        for(String permission:permissionsByUserName) {
            info.addStringPermission(permission);
        }
        info.setRoles(roles);
        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
       System.out.println("执行了doGetAuthenticationInfo 认真");
       UsernamePasswordToken userToken=(UsernamePasswordToken)authenticationToken;
       User user=userMapper.selectUserByaccoun_id(userToken.getUsername());
        String oringnPassword = new String((char[]) userToken.getCredentials());
//        String salt = user.getSalt();
//        System.out.println(userToken.getUsername()+"===="+oringnPassword);
//        String encodedPassword = ShiroMd5Util.shiroEncryption(oringnPassword,salt);
//        System.out.println("密码："+encodedPassword);
       if (user==null){
           return null;
       }
       //可以加密
       return new SimpleAuthenticationInfo(user,oringnPassword,getName());
    }
}
