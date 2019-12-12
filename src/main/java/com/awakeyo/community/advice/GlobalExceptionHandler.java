package com.awakeyo.community.advice;

import com.awakeyo.community.exception.CustomizeException;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author awakeyoyoyo
 * @className GlobalExceptionHandler
 * @description TODO
 * @date 2019-12-12 20:46
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ModelAndView handlerException(Exception e, HttpServletRequest request, Model model){
        //传入自己的错误状态码 4XX 5XX
        HttpStatus status=getStatus(request);
        model.addAttribute("status",status.value());
        if (e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());
        }
       else{
            model.addAttribute("message","老兵之家炸了，稍后再来看吧");
        }
        return  new ModelAndView("error");
    }
    private HttpStatus getStatus(HttpServletRequest request){
        Integer statusCode=(Integer)request.getAttribute("javax.servlet.error.status_code");
        if (statusCode==null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
