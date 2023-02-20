package com.campingmapping.team4.spring.t436mall.model.service.impl;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.common.collect.Lists;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GoogleFileUtil {
    // public static void main(String[] args) throws IOException {
    //     String filepath = uploadFile("finger", "ryantestpicture");
    //     System.out.println("公开访问地址是：：：：" + filepath);
    // }

    public static String uploadFile(String fileName,String bucketName) throws IOException {
        //要上传的本地文件的绝对路径
        String filePath = "C:/Users/User/Pictures/images009.jpg";

        //读取本地存储的服务账号的json密钥，拿到该服务账号的权限
        GoogleCredentials credentials= GoogleCredentials.fromStream(new FileInputStream("C:/Users/User/Downloads/applied-tractor-376716-f3bc9fb13e63.json"))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));

        //创建服务账号对应的操作对象
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        // 将本地指定路径的文件转换为字节
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));

        //上传文件图片到指定的存储桶中
        BlobId blobId=BlobId.of(bucketName,fileName);
        BlobInfo blobInfo=BlobInfo.newBuilder(blobId).build();
        Blob blob = storage.create(blobInfo, bytes);

        //修改已经上传的文件类型为 image/jpg
        blob.toBuilder().setContentType("image/jpg").build().update();
        
        //返回公开访问的地址
        return "https://storage.googleapis.com/" + bucketName + "/" + fileName;
    }

}
