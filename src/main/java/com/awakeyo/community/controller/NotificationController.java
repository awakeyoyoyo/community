package com.awakeyo.community.controller;

import com.awakeyo.community.pojo.User;
import com.awakeyo.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author awakeyoyoyo
 * @className NotificationController
 * @description TODO
 * @date 2020-02-08 21:32
 */
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/notification/{type}/{id}/{questionId}")
    public String profile(HttpServletRequest request,@PathVariable("type")Integer type, @PathVariable(name="id")Integer id,@PathVariable("questionId")Integer questionId){
        User user=(User)request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        notificationService.read(id,user.getId());
        if (questionId==10086){
            return "redirect:/commentsRecord";
        }
        if (type==1){
            return "redirect:/question/"+questionId;
        }else if (type==2){

            return "redirect:/question/"+questionId;
        }else {
            return "redirect:/Aritle/"+questionId;
        }
    }
}
