package com.awakeyo.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author awakeyoyoyo
 * @className PublishController
 * @description TODO
 * @date 2019-12-03 20:33
 */
@Controller
public class PublishController {
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
}
