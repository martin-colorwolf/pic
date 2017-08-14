package com.wang.out.common;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.sign.Credentials;

/**
 * Created by Martin.Wang on 2017/8/10.
 */
public class Qcloud {

    public static COSClient cosClient;
    public static Credentials cred;



    public static COSClient getCosClient() {
        // 初始化客户端配置
        ClientConfig clientConfig = new ClientConfig();
        // 设置bucket所在的区域，比如华南园区：gz； 华北园区：tj；华东园区：sh ；
        clientConfig.setRegion("sh");
        // 初始化cosClient

        return new COSClient(clientConfig, Qcloud.getCred());
    }

    public static void setCosClient(COSClient cosClient) {
        Qcloud.cosClient = cosClient;
    }

    public static Credentials getCred() {
        long appId = 1251972445;
        String secretId = "AKIDZJtKepgdGhwzzsF5uqfJn8GDza2H9DtO";
        String secretKey = "bozza5Xm1GMGyUnupY5QZVEkaKsDMXdY";
        // 设置要操作的bucket
        String bucketName = "muyoucun";
        // 初始化秘钥信息


        return new Credentials(appId, secretId, secretKey);
    }

    public static void setCred(Credentials cred) {
        Qcloud.cred = cred;
    }
}
