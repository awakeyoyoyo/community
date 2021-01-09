package com.awakeyo.community.service;

import org.springframework.stereotype.Service;

/**
 * @author awakeyoyoyo
 * @className TestAopService
 * @description TODO
 * @date 2020-05-12 09:22
 */
@Service
public class TestAopService {
    private String name="lqhao";
    public String findUserById() {
        System.out.println("***************执行业务方法findUser,查找的用户名字为:"+name);
        return name;
    }

    public void addUser() {
        System.out.println("***************执行业务方法addUser****************");
        throw new RuntimeException();
    }


    public void setName(String name) {
        this.name = name;
    }
}
