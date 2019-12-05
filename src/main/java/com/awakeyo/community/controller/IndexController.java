package com.awakeyo.community.controller;
import com.awakeyo.community.dto.PageResult;
import com.awakeyo.community.dto.Question;
import com.awakeyo.community.dto.QuestionDTO;
import com.awakeyo.community.dto.User;
import com.awakeyo.community.mapper.QuestionMapper;
import com.awakeyo.community.mapper.UserMapper;
import com.awakeyo.community.service.QustionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author awakeyoyoyo
 * @className HelloController
 * @description TODO
 * @date 2019-11-30 21:13
 */
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QustionService qustionService;
    /**
     * Method Description
     * @author awakeyoyoyo
     * @date 2019-11-30
     * @params [name, model]
     * @return java.lang.String
     */
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "pageNo",defaultValue = "1" ) Integer pageNo,
                        @RequestParam(name = "pageSize",defaultValue ="5") Integer pageSize){
        Object obj;
        obj=request.getSession().getAttribute("user");
        if (obj==null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie c : cookies) {
                if (c.getName().equals("token")) {
                    String token = c.getValue();
                    if (token != null) {
                        User user = userMapper.findByToken(token);
                        if (user != null) {
                            request.getSession().setAttribute("user", user);
                        }
                        break;
                    }
                }
            }
        }
        PageResult<QuestionDTO> pageResult=qustionService.getList(pageNo,pageSize);
        model.addAttribute("pageResult",pageResult);
        return "index";
    }


}
