package com.awakeyo.community.pojo.dto;

import lombok.Data;

/**
 * @author awakeyoyoyo
 * @className ChangePwdDto
 * @description TODO
 * @date 2020-02-26 20:32
 */
@Data
public class ChangePwdDto {
    private String phone;
    private String code;
    private String newPassword;
}
