package com.awakeyo.community.controller;

import com.awakeyo.community.pojo.dto.ImageEditDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author awakeyoyoyo
 * @className FileUploadController
 * @description TODO
 * @date 2020-02-12 09:09
 */
@Controller
public class FileUploadController {
    @RequestMapping("/image/upload")
    @ResponseBody
    public ImageEditDto upload(){
        ImageEditDto imageEditDto=new ImageEditDto();
        imageEditDto.setSuccess(1);
        imageEditDto.setUrl("/images/loading.gif");
        return imageEditDto;
    }
}
