package com.awakeyo.community.util;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author awakeyoyoyo
 * @className ShiroMd5Util
 * @description TODO
 * @date 2020-03-12 15:21
 */
public class ShiroMd5Util {
    public static String shiroEncryption(String password,String salt) {
        // shiro 自带的工具类生成salt
        //String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 加密次数
        int times = 2;
        // 算法名称
        String algorithmName = "md5";

        String encodedPassword = new SimpleHash(algorithmName,password,salt,times).toString();

        // 返回加密后的密码
        return encodedPassword;
    }

}
