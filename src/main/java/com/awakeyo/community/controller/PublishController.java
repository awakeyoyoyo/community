package com.awakeyo.community.controller;

import com.awakeyo.community.cache.TagCache;
import com.awakeyo.community.pojo.Question;
import com.awakeyo.community.mapper.QuestionMapper;
import com.awakeyo.community.pojo.User;
import com.awakeyo.community.service.QustionService;
import com.awakeyo.community.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author awakeyoyoyo
 * @className PublishController
 * @description TODO
 * @date 2019-12-03 20:33
 */
@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QustionService qustionService;
    @Autowired
    private RedisUtil redisUtil;
    @GetMapping("/edit")
    public String edit(Integer id,Model model){
        Question question=questionMapper.selectByPrimaryKey(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDecription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.getInstance().get());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.getInstance().get());
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id") Integer id,
            HttpServletRequest request,
            Model model
    ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", TagCache.getInstance().get());
        User user;
        user=(User)request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error","用户未登陆！！！！");
            return "publish";
        }
        if (title==null||title==""){
            model.addAttribute("error","标题不能为空！！！！");
            return "publish";
        }
        if (description==null||description==""){
            model.addAttribute("error","问题描述不能为空！！！！");
            return "publish";
        }
        if (tag==null||tag==""){
            model.addAttribute("error","标签不能为空！！！！");

            return "publish";
        }
        String invalid=TagCache.getInstance().filterInvalid(tag);
        if (!StringUtils.isEmpty(invalid)){
            model.addAttribute("error","输入非法标签"+invalid);
            return "publish";
        }
        Question question=new Question();
        question.setTitle(title);
        question.setDecription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        qustionService.insertOrUpdate(question);
        Integer questionCount=questionMapper.selectAll();
        redisUtil.set("questionCount",questionCount);
        model.addAttribute("tags", TagCache.getInstance().get());

        return "redirect:/community";
    }
}
