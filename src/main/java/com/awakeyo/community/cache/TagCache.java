package com.awakeyo.community.cache;

import com.awakeyo.community.pojo.dto.TagDTO;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author awakeyoyoyo
 * @className TagCache
 * @description TODO
 * @date 2020-01-14 19:02
 */
@Data
public class TagCache {
    private static TagCache instance = new TagCache();
    private static List<TagDTO> tagDTOS;
    private TagCache(){
        tagDTOS=new ArrayList<>();
        TagDTO wather=new TagDTO();
        wather.setCategoryName("吹水系列");
        wather.setTags(Arrays.asList("烦恼事儿","逗比趣事","日常生活","面试"));
        tagDTOS.add(wather);

        TagDTO program=new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js","php","html","java","c#","python","go"));
        tagDTOS.add(program);

        TagDTO server=new TagDTO();
        server.setCategoryName("后台技术");
        server.setTags(Arrays.asList("linux","redis","SpringBoot","apache","mybatis","shell","数据库"));
        tagDTOS.add(server);

        TagDTO other=new TagDTO();
        other.setCategoryName("其他");
        other.setTags(Arrays.asList("=。=","-。-","=。-","=。=","-。-","=。-","=。=","-。-","=。-","=。=","-。-","=。-","=。=","-。-","=。-","=。=","-。-","=。-","=。=","-。-","=。-"));
        tagDTOS.add(other);
    }
    public static TagCache getInstance(){
        return instance;
    }
    public List<TagDTO> get(){
        return tagDTOS;
    }
    public String filterInvalid(String tags){
       String[] split = tags.split(",");
       List<TagDTO> tagDTOS=get();
       List<String> tagList=tagDTOS.stream().flatMap(tag->tag.getTags().stream()).collect(Collectors.toList());
       String invalid=Arrays.stream(split).filter(t->!tagList.contains(t)).collect(Collectors.joining(","));
       return invalid;
    }
}
