package com.awakeyo.community.controller;

import com.awakeyo.community.common.WebResponse;
import com.awakeyo.community.mapper.UserMapper;
import com.awakeyo.community.pojo.User;
import com.awakeyo.community.pojo.dto.ChangePwdDto;
import com.awakeyo.community.pojo.dto.RegisterDto;
import com.awakeyo.community.provider.AlibabaMsgProvider;
import com.awakeyo.community.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private AlibabaMsgProvider alibabaMsgProvider;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        Subject subject=SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/";
    }
    @PostMapping("/login")
    public String login(@RequestParam(name = "loginPhone")String phone,
                        @RequestParam("password") String password,
                        @RequestParam(name = "remember",defaultValue = "true") boolean remember,
                        HttpServletResponse response, HttpServletRequest request, Model model){
        if (phone.isEmpty()){
            model.addAttribute("errorMxg","手机号不能为空");
            return "login";
        }
        if (password.isEmpty()){
            model.addAttribute("errorMxg","密码不能为空");
            return "login";
        }
        //获取当前用户
        Subject subject= SecurityUtils.getSubject();
        //封装登陆
        UsernamePasswordToken token=new UsernamePasswordToken(phone,password);
        try {
            subject.login(token);
        }catch (UnknownAccountException uae) {
            model.addAttribute("errorMxg","此手机号没有注册");
            return "login";
            //未知手机号
        } catch (IncorrectCredentialsException ice) {
            model.addAttribute("errorMxg","密码错误！！！");
            return "login";
            //密码错误
        } catch (LockedAccountException lae) {
            model.addAttribute("errorMxg","你的账号被管理员封了，请联系管理员");
            return "login";
            //用户被锁住了
        }
        catch (Exception e) {
            //未知错误
            model.addAttribute("errorMxg","未知错误登陆失败，请联系管理员");
            return "login";
        }
        User user=(User) subject.getPrincipal();
        userService.login(user,response);
        request.getSession().setAttribute("user",user);
        return "redirect:/";
    }
    @GetMapping("/tologin")
    public String tologin(Model model){
        model.addAttribute("errorMxg","请先登陆再操作噢");
        return "login";

    }
    @GetMapping("/toerror")
    public String toerror(Model model){
        model.addAttribute("message","你小子，你没有相关权利噢，这是管理员才有的权利");
        return "error";

    }
    @PostMapping("/register")
    public String register(RegisterDto registerDto,Model model){
        if (registerDto.getPhone().isEmpty()){
            model.addAttribute("errorMxg","手机号不能为空");
            return "register";
        }
        if (registerDto.getCode().isEmpty()){
            model.addAttribute("errorMxg","验证码不能为空");
            return "register";
        }
        if (registerDto.getUsername().isEmpty()){
            model.addAttribute("errorMxg","用户名不能为空");
            return "register";
        }
        if (registerDto.getPassword().isEmpty()){
            model.addAttribute("errorMxg","密码不能为空");
            return "register";
        }
        if (registerDto.getPassword().length()<6){
            model.addAttribute("errorMxg","密码太短啦！！！");
            return "login";
        }
        WebResponse webResponse=userService.register(registerDto);
        if (webResponse.isSuccess()){
            model.addAttribute("Mxg","注册好了请登录试试看");
            return  "login";
        }else {
            model.addAttribute("errorMxg",webResponse.getMsg());
            return  "register";
        }
    }

    @GetMapping("/getCode")
    @ResponseBody
    public WebResponse getCode(@RequestParam("phone")String phone){
        try {
            if (userMapper.selectByaccoun_id(phone)!=null){
                return WebResponse.createByError();
            }
            alibabaMsgProvider.sendMsg(phone);
            return WebResponse.createBySuccess();
        }catch (Exception e){
            return WebResponse.createByError();
        }
    }
    @PostMapping("/chargePassword")
    public String chargePassword(ChangePwdDto changePwdDto, Model model){
        if (changePwdDto.getPhone().isEmpty()){
            model.addAttribute("errorMxg","手机号不能为空");
            return "login";
        }
        if (changePwdDto.getCode().isEmpty()){
            model.addAttribute("errorMxg","验证码不能为空");
            return "login";
        }
        if (changePwdDto.getNewPassword().isEmpty()){
            model.addAttribute("errorMxg","新密码不能为空");
            return "login";
        }
        if (changePwdDto.getNewPassword().length()<6){
            model.addAttribute("errorMxg","新密码过短！！！");
            return "login";
        }
        WebResponse webResponse=userService.chargePwd(changePwdDto);
        if (webResponse.isSuccess()){
            model.addAttribute("Mxg","修改好了请登录试试看");
            return  "login";
        }else {
            model.addAttribute("errorMxg",webResponse.getMsg());
            return  "login";
        }
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
