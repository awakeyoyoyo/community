package com.awakeyo.community.controller;

import com.awakeyo.community.dto.GithubAccessTokenDTO;
import com.awakeyo.community.dto.GithubUser;
import com.awakeyo.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
                           @RequestParam(name="state") String state){
        GithubAccessTokenDTO githubAccessTokenDTO = new GithubAccessTokenDTO();
        githubAccessTokenDTO.setClient_id(clientId);
        githubAccessTokenDTO.setClient_secret(clientSecret);
        githubAccessTokenDTO.setCode(code);
        githubAccessTokenDTO.setRedirect_uri(redirecUri);
        githubAccessTokenDTO.setState(state);
        //获取accesstoken
        String accessToken = githubProvider.getAccessToken(githubAccessTokenDTO);
        //利用accesstoken获取user信息
        GithubUser user=githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
