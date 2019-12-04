package com.awakeyo.community.dto;

import lombok.Data;

/**
 * @author awakeyoyoyo
 * @className GithubAccessTokenDTO
 * @description TODO
 * @date 2019-12-01 21:25
 */
@Data
public class GithubAccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
