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
    @GetMapping("/notification/{id}/{questionId}")
    public String profile(HttpServletRequest request, @PathVariable(name="id")Integer id,@PathVariable("questionId")Integer questionId){
        User user=(User)request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        notificationService.read(id,user.getId());
        return "redirect:/question/"+questionId;
    }
}
