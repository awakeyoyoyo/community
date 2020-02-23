package com.awakeyo.community.util;

import java.util.Random;

/**
 * @author awakeyoyoyo
 * @className NumberUtil
 * @description TODO
 * @date 2020-02-23 23:33
 */
public class NumberUtil {
    public static  String randomNumber(){
        String str = "";
        char[] ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        Random random = new Random();
        for (int i = 0; i <6; i++){
            char num = ch[random.nextInt(ch.length)];
            str += num;
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(NumberUtil.randomNumber());
    }
}
