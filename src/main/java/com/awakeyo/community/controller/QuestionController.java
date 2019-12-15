package com.awakeyo.community.controller;

import com.awakeyo.community.pojo.dto.QuestionDTO;
import com.awakeyo.community.service.QustionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author awakeyoyoyo
 * @className QuestionController
 * @description TODO
 * @date 2019-12-10 17:46
 */
@Controller
public class QuestionController {
    @Autowired
    private QustionService qustionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                        Model model){
        QuestionDTO questionDTO=qustionService.getByIdAndIncView(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
