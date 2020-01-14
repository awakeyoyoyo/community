package com.awakeyo.community.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author awakeyoyoyo
 * @className TagDTO
 * @description TODO
 * @date 2020-01-14 19:11
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
