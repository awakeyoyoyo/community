package com.awakeyo.community.advice;

import com.alibaba.fastjson.JSON;
import com.awakeyo.community.common.WebResponse;
import com.awakeyo.community.exception.CustomizeException;
import com.awakeyo.community.exception.RedisException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author awakeyoyoyo
 * @className GlobalExceptionHandler
 * @description TODO
 * @date 2019-12-12 20:46
 */
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(Exception.class)
//    public ModelAndView handlerException(Exception e, HttpServletRequest request,
//                                         Model model, HttpServletResponse response
//    ){
//        String contentType=request.getContentType();
//        if ("application/json".equals(contentType)){
//            WebResponse serverResponse;
//            if (e instanceof CustomizeException){
//                serverResponse= WebResponse.createByErrorMessage(e.getMessage());
//            } else if (e instanceof RedisException) {
//                serverResponse= WebResponse.createByErrorMessage(e.getMessage());
//            } else {
//                serverResponse = WebResponse.createByErrorMessage("老兵之家炸了，稍后再来看吧");
//            }
//            try {
//                response.setContentType("application/json");
//                response.setStatus(200);
//                response.setCharacterEncoding("utf-8");
//                PrintWriter writer=response.getWriter();
//                writer.write(JSON.toJSONString(serverResponse));
//                writer.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//            return null;
//        }
//        else {
//            //传入自己的错误状态码 4XX 5XX
//            HttpStatus status = getStatus(request);
//            model.addAttribute("status", status.value());
//            if (e instanceof CustomizeException) {
//                model.addAttribute("message", e.getMessage());
//            } else if (e instanceof RedisException) {
//                model.addAttribute("message", e.getMessage());
//            }else {
//                model.addAttribute("message", "老兵之家炸了，稍后再来看吧");
//            }
//            return new ModelAndView("error");
//        }
//    }
//    private HttpStatus getStatus(HttpServletRequest request){
//        Integer statusCode=(Integer)request.getAttribute("javax.servlet.error.status_code");
//        if (statusCode==null){
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return HttpStatus.valueOf(statusCode);
//    }
//}
