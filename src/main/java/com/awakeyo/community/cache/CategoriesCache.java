package com.awakeyo.community.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author awakeyoyoyo
 * @className CategoriesCache
 * @description TODO
 * @date 2020-03-01 21:18
 */
public class CategoriesCache {
    private static CategoriesCache instance = new CategoriesCache();
    private static List<String> categories;
    private CategoriesCache(){
        categories=new ArrayList<String>();
        categories.add("java");
        categories.add("SpringBoot");
        categories.add("Linux");
        categories.add("JVM");
        categories.add("设计模式");
        categories.add("解决BUG");
        categories.add("算法题");
        categories.add("面试");
        categories.add("数据库");
        categories.add("服务器");
        categories.add("计算机网络");
        categories.add("后台技术ing");
    }
    public static CategoriesCache getInstance(){
        if (instance==null){
            instance=new CategoriesCache();
        }
        return instance;
    }
    public List<String> get(){
        return categories;
    }
    public String filterInvalid(String tags){
        String[] split = tags.split(",");
        List<String> tagDTOS=get();
        List<String> tagList=categories;
        String invalid= Arrays.stream(split).filter(t->!tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
