package com.awakeyo.community.controller;

import com.awakeyo.community.cache.CategoriesCache;
import com.awakeyo.community.pojo.PageResult;
import com.awakeyo.community.pojo.dto.ArticleCategoryDto;
import com.awakeyo.community.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author awakeyoyoyo
 * @className CategoriesController
 * @description TODO
 * @date 2020-03-01 21:54
 */
@Controller
public class CategoriesController {
    @Autowired
    private CategoriesService categoriesService;
    @GetMapping("/categories")
    public String categories(@RequestParam(name="tag",defaultValue = "java")String tag,
                             Model model,
                             @RequestParam(name = "pageNo", defaultValue = "1", required = false) Integer pageNo,
                             @RequestParam(name = "pageSize", defaultValue = "5", required = false)Integer pageSize){
        PageResult<ArticleCategoryDto> articleCategoryDtos=categoriesService.getDto(tag,pageSize,pageNo);
        model.addAttribute("tag",tag);
        model.addAttribute("pageResult",articleCategoryDtos);
        return "blogTag";
    }
}
