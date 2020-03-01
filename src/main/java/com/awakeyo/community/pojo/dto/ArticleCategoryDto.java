package com.awakeyo.community.pojo.dto;

import lombok.Data;

/**
 * @author awakeyoyoyo
 * @className ArticleCategoryDto
 * @description TODO
 * @date 2020-03-01 22:07
 */
@Data
public class ArticleCategoryDto {
    private Integer id;
    private String title;
    private String tag;
    private Long gmtCreate;
}
