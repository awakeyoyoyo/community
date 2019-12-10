package com.awakeyo.community.controller;

import com.awakeyo.community.dto.GithubAccessTokenDTO;
import com.awakeyo.community.dto.GithubUser;
import com.awakeyo.community.dto.User;
import com.awakeyo.community.mapper.UserMapper;
import com.awakeyo.community.provider.GithubProvider;
import com.awakeyo.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * @author awakeyoyoyo
 * @className GitHubAuthorizeController
 * @description TODO
 * @date 2019-12-01 19:57
 */
@Controller
public class GitHubAuthorizeController {
    @Autowired
    private UserService userService;
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirecUri;
    /**
     * Method Description
     * @author awakeyoyoyo
     * @date 2019-12-01
     * @params [code, state]
     * @return java.lang.String
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletResponse response){
        GithubAccessTokenDTO githubAccessTokenDTO = new GithubAccessTokenDTO();
        githubAccessTokenDTO.setClient_id(clientId);
        githubAccessTokenDTO.setClient_secret(clientSecret);
        githubAccessTokenDTO.setCode(code);
        githubAccessTokenDTO.setRedirect_uri(redirecUri);
        githubAccessTokenDTO.setState(state);
        //获取accesstoken
        String accessToken = githubProvider.getAccessToken(githubAccessTokenDTO);
        //利用accesstoken获取user信息
        GithubUser githubUser=githubProvider.getUser(accessToken);
        if (githubUser!=null&&githubUser.getId()!=null){
            //登陆成功写入cookie和session
            User user=new User();
            String token=UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setBio(githubUser.getBio());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userService.createOrUpdate(user);
            //设置cookie
            Cookie cookie=new Cookie("token",token);
            cookie.setMaxAge(60*60*24);
            response.addCookie(cookie);
            return "redirect:/";
        }
        else {
            //登陆失败，重新登录
            return "redirect:/";
        }
    }
}
