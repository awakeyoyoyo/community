package com.awakeyo.community.pojo;

import lombok.Data;

/**
 * @author awakeyoyoyo
 * @className GithubUser
 * @description TODO
 * @date 2019-12-01 22:45
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;
}
