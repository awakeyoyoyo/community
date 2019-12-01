package com.awakeyo.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author awakeyoyoyo
 * @className HelloController
 * @description TODO
 * @date 2019-11-30 21:13
 */
@Controller
public class HelloController {
    /**
     * Method Description
     * @author awakeyoyoyo
     * @date 2019-11-30
     * @params [name, model]
     * @return java.lang.String
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
