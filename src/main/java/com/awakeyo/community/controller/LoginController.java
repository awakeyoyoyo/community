package com.awakeyo.community.controller;

import com.awakeyo.community.common.WebResponse;
import com.awakeyo.community.pojo.User;
import com.awakeyo.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Autowired
    private UserService userService;
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        return "redirect:/";
    }
    @PostMapping("/login")
    public String login(@RequestParam(name = "loginPhone")String phone,
                        @RequestParam("password") String password,
                        @RequestParam("remember") boolean remember,
                        HttpServletResponse response, HttpServletRequest request, Model model){
        WebResponse<User> webResponse=userService.login(phone,password,remember,response);
        if (webResponse.isSuccess()){
            request.getSession().setAttribute("user",webResponse.getData());
            return "redirect:/";
        }else {
            model.addAttribute("errorMxg",webResponse.getMsg());
            return "login";
        }


    }
    @PostMapping("/register")
    public String register(){
        return null;
    }
    @GetMapping("/phoneLogin")
    public String loginHtml(){
        return "login";
    }

    @GetMapping("/phoneRegister")
    public String registerHtml(){
        return "register";
    }

}
