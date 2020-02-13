package com.awakeyo.community.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author awakeyoyoyo
 * @className AlibabaOssProvider
 * @description TODO
 * @date 2020-02-13 22:27
 */
@Component
public class AlibabaOssProvider {
    @Value("${alibabaoss.accessKeyId}")
    private String accessKeyId;
    @Value("${alibabaoss.accessKeyId}")
    private String accessKeySecret;
    @Value("${alibabaoss.bucketName}")
    private String bucketName;
    @Value("${alibabaoss.endpoint}")
    private String endpoint;

}
