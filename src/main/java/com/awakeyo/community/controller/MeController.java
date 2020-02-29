package com.awakeyo.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author awakeyoyoyo
 * @className MeController
 * @description TODO
 * @date 2020-02-29 21:16
 */
@Controller
public class MeController {
    @GetMapping("/aboutMe")
    public String aboutMe(){
        return "aboutMe";
    }
}
