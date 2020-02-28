package com.awakeyo.community.controller;

import com.awakeyo.community.pojo.Article;
import com.awakeyo.community.pojo.PageResult;
import com.awakeyo.community.pojo.dto.ArticleDto;
import com.awakeyo.community.pojo.dto.QuestionDTO;
import com.awakeyo.community.service.ArticleService;
import com.awakeyo.community.service.NotificationService;
import com.awakeyo.community.service.QustionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author awakeyoyoyo
 * @className HelloController
 * @description TODO
 * @date 2019-11-30 21:13
 */
@Controller
public class IndexController {
    @Autowired
    private QustionService qustionService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ArticleService articleService;
    /**
     * Method Description
     *
     * @return java.lang.String
     * @author awakeyoyoyo
     * @date 2019-11-30
     * @params [name, model]
     */
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "pageNo", defaultValue = "1", required = false) Integer pageNo,
                        @RequestParam(name = "pageSize", defaultValue = "3", required = false) Integer pageSize,
                        @RequestParam(name = "search", defaultValue = "") String search) {
        PageResult<ArticleDto> pageResult;
        if (!StringUtils.isEmpty(search)) {
            pageResult = articleService.getListSearch(search, pageNo, pageSize);

        } else {
            pageResult = articleService.getList(pageNo, pageSize);
        }
        model.addAttribute("pageResult", pageResult);
        model.addAttribute("search", search);
        return "index";
    }

    @GetMapping("/community")
    public String community(Model model,
                        @RequestParam(name = "pageNo", defaultValue = "1", required = false) Integer pageNo,
                        @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                        @RequestParam(name = "search", defaultValue = "") String search) {
        PageResult<QuestionDTO> pageResult;
        if (!StringUtils.isEmpty(search)) {
            pageResult = qustionService.getListSearch(search, pageNo, pageSize);

        } else {
            pageResult = qustionService.getList(pageNo, pageSize);
        }
        model.addAttribute("pageResult", pageResult);
        model.addAttribute("search", search);
        return "community";
    }

}
