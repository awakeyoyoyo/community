package com.awakeyo.community.controller;

import com.awakeyo.community.pojo.dto.ImageEditDto;
import com.awakeyo.community.provider.AlibabaOssProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static org.springframework.util.ResourceUtils.getFile;

/**
 * @author awakeyoyoyo
 * @className FileUploadController
 * @description TODO
 * @date 2020-02-12 09:09
 */
@Controller
public class FileUploadController {
    @Autowired
    private AlibabaOssProvider alibabaOssProvider;
    @Value("${alibabaoss.communityfolder}")
    private String folder;
    @Value("${alibabaoss.url}")
    private String endpoint;
    @RequestMapping("/image/upload")
    @ResponseBody
    public ImageEditDto Imageupload(@RequestParam(value = "editormd-image-file") MultipartFile file, HttpServletRequest request){
        String fileName="";
        try {
            fileName=alibabaOssProvider.upload(file.getInputStream(),file.getContentType(),file.getOriginalFilename(),folder);
            if (fileName==null){
                fileName="gg.jpg";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageEditDto imageEditDto=new ImageEditDto();
        imageEditDto.setSuccess(1);
        imageEditDto.setUrl(endpoint+fileName);
        return imageEditDto;
    }
}
