package com.awakeyo.community.controller;

import com.awakeyo.community.dto.GithubAccessTokenDTO;
import com.awakeyo.community.dto.GithubUser;
import com.awakeyo.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author awakeyoyoyo
 * @className GitHubAuthorizeController
 * @description TODO
 * @date 2019-12-01 19:57
 */
@Controller
public class GitHubAuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    /**
     * Method Description
     * @author awakeyoyoyo
     * @date 2019-12-01
     * @params [code, state]
     * @return java.lang.String
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name="state") String state){
        GithubAccessTokenDTO githubAccessTokenDTO = new GithubAccessTokenDTO();
        githubAccessTokenDTO.setClient_id("f25c1cabc9f06377fa85");
        githubAccessTokenDTO.setClient_secret("1288c68cf3a12166b03f85ce31ec120ee60ab172");
        githubAccessTokenDTO.setCode(code);
        githubAccessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        githubAccessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(githubAccessTokenDTO);
        GithubUser user=githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
