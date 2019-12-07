package com.awakeyo.community.controller;

import com.awakeyo.community.dto.PageResult;
import com.awakeyo.community.dto.QuestionDTO;
import com.awakeyo.community.dto.User;
import com.awakeyo.community.mapper.UserMapper;
import com.awakeyo.community.service.QustionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
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
    private UserMapper userMapper;
    @Autowired
    private QustionService qustionService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "pageNo",defaultValue = "1" ) Integer pageNo,
                          @RequestParam(name = "pageSize",defaultValue ="5") Integer pageSize){
        Object obj;
        User user;
        user=(User)request.getSession().getAttribute("user");
        if (user==null) {
            Cookie[] cookies = request.getCookies();
            if (cookies!=null&&cookies.length!=0) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("token")) {
                        String token = c.getValue();
                        if (token != null) {
                            user = userMapper.findByToken(token);
                            if (user != null) {
                                request.getSession().setAttribute("user", user);
                            }
                            break;
                        }
                    }
                }
            }
        }
        if (user==null){
            model.addAttribute("error","用户未登陆");
            return "redirect:/";
        }
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","我的回复");
        }
        PageResult<QuestionDTO> pageResult=qustionService.getListByUserid(user.getId(),pageNo,pageSize);
        model.addAttribute("pageResult",pageResult);
        return "/profile";
    }
}
