package com.awakeyo.community.controller;

import com.awakeyo.community.pojo.PageResult;
import com.awakeyo.community.pojo.User;
import com.awakeyo.community.pojo.dto.NotificationDTO;
import com.awakeyo.community.pojo.dto.QuestionDTO;
import com.awakeyo.community.service.NotificationService;
import com.awakeyo.community.service.QustionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;

/**
 * @author awakeyoyoyo
 * @className ProfileController
 * @description TODO
 * @date 2019-12-07 23:30
 */
@Controller
public class ProfileController {
    @Autowired
    private QustionService qustionService;
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "pageNo",defaultValue = "1" ) Integer pageNo,
                          @RequestParam(name = "pageSize",defaultValue ="5") Integer pageSize){
        User user;
        user=(User)request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error","用户未登陆");
            return "redirect:/";
        }
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","提问");
            model.addAttribute("title","提问 - 与我常在's blog");
            PageResult<QuestionDTO> pageResult=qustionService.getListByUserid(user.getId(),pageNo,pageSize);
            model.addAttribute("pageResult",pageResult);
        }else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","通知");
            model.addAttribute("title","通知 - 与我常在's blog");
            PageResult<NotificationDTO> pageResult=notificationService.getListByUserid(user.getId(),pageNo,pageSize);
            model.addAttribute("pageResult",pageResult);
        }
        Long unreadCount=notificationService.getUnreadCount(user.getId());
        model.addAttribute("unreadCount",unreadCount);
        return "profile";
    }
}
