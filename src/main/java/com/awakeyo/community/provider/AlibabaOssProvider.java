package com.awakeyo.community.provider;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.StorageClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author awakeyoyoyo
 * @className AlibabaOssProvider
 * @description TODO
 * @date 2020-02-13 22:27
 */
@Service
public class AlibabaOssProvider {
    @Value("${alibabaoss.accessKeyId}")
    private String accessKeyId;
    @Value("${alibabaoss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${alibabaoss.bucketName}")
    private String bucketName;
    @Value("${alibabaoss.endpoint}")
    private String endpoint;
    public String upload(InputStream fileStream,String mineType,String fileName,String folder){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        //上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String generateFileName=folder+"/";
        String[] fileSpliter=fileName.split("\\.");
        if (fileSpliter.length>1){
            generateFileName= generateFileName+UUID.randomUUID().toString()+"."+fileSpliter[fileSpliter.length-1];
        }else {
            return null;
        }
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, generateFileName,fileStream);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpg");
        putObjectRequest.setMetadata(metadata);
        // 上传内容到指定的存储空间（bucketName）并保存为指 定的文件名称（objectName）。
        ossClient.putObject(putObjectRequest);
        // 关闭OSSClient。
        ossClient.shutdown();
        return "/"+generateFileName;
    }
}
