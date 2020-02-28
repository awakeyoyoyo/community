package com.awakeyo.community.controller;

import com.awakeyo.community.pojo.PageResult;
import com.awakeyo.community.pojo.Question;
import com.awakeyo.community.pojo.dto.QuestionDTO;
import com.awakeyo.community.service.QustionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
        List<Question> questions=qustionService.selectRelater(id,questionDTO.getTag());
        model.addAttribute("relateQuestions",questions);
        model.addAttribute("question",questionDTO);
        return "question";
    }

    @GetMapping("/questions")
    public String question( @RequestParam(name = "pageNo", defaultValue = "1", required = false) Integer pageNo,
                            @RequestParam(name = "pageSize", defaultValue = "3", required = false) Integer pageSize,
                            Model model){
        PageResult<QuestionDTO> pageResult;
        pageResult = qustionService.getList(pageNo, pageSize);
        model.addAttribute("pageResult",pageResult);
        return "community::questions";
    }
}
