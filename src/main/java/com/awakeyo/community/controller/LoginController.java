package com.awakeyo.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author awakeyoyoyo
 * @className LoginController
 * @description TODO
 * @date 2019-12-10 21:11
 */
@Controller
public class LoginController {
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
