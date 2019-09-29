package com.fit2cloud.support.service.uploadfile.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.fit2cloud.commons.utils.LogUtil;
import com.fit2cloud.commons.utils.PropertiesConfigurer;
import com.fit2cloud.support.common.constants.RepositoryConst;
import com.fit2cloud.support.service.uploadfile.OperationFileService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

@Service
public class OSSSupportFileServiceImpl implements OperationFileService {

    private String accessKey = PropertiesConfigurer.getProperty(RepositoryConst.Attribute.OSS_ACCESS_KEY.getValue());

    private String securetKey = PropertiesConfigurer.getProperty(RepositoryConst.Attribute.OSS_SECRET_KEY.getValue());

    private String bucketTicketAttachmentName = PropertiesConfigurer.getProperty(RepositoryConst.Attribute.BUCKET_TICKET_ATTACHMENT.getValue());

    private String ossLocation = PropertiesConfigurer.getProperty(RepositoryConst.Attribute.OSS_LOCATION.getValue());

    /**
     * 获取工单oss初始化
     *
     * @return
     */
    public OSS getBucketTicketOSSInit() {
        String endpoint = "http://" + ossLocation + ".aliyuncs.com";
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKey, securetKey);
        try {
            if (!ossClient.doesBucketExist(bucketTicketAttachmentName)) {
                LogUtil.error(" bucket " + bucketTicketAttachmentName + "not exist!\n");
                throw new Exception("bucket" + bucketTicketAttachmentName + "not exist!");
            }
        } catch (Exception e) {
            LogUtil.error("{}", e);
        }
        return ossClient;
    }

    /**
     * 保存工单文件到OSS中
     *
     * @param inputStream
     * @param fileKey
     * @return
     * @throws Exception
     */
    public Boolean saveTicketFile(InputStream inputStream, String fileKey) throws Exception {
        OSS ossClient = getBucketTicketOSSInit();
        ossClient.putObject(bucketTicketAttachmentName, fileKey, inputStream);
        ossClient.shutdown();
        return true;
    }

    @Override
    public RepositoryConst getObjectStorageType() {
        return RepositoryConst.OSS;
    }

    @Override
    public Boolean saveFile(OSS ossClient, String bucketName, InputStream inputStream, String fileKey) throws Exception {
        ossClient.putObject(bucketName, fileKey, inputStream);
        ossClient.shutdown();
        return true;
    }

    @Override
    public URL getFileUrl(OSS ossClient, String bucketName, String filePath) {
        // 设置URL过期时间为12小时。
        Date expiration = new Date(System.currentTimeMillis() + 12 * 3600 * 1000);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(bucketName, filePath, expiration);
        ossClient.shutdown();
        return url;
    }

    @Override
    public byte[] downFile(OSS ossClient, String bucketName, String fileKey) throws IOException {
        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(bucketName, fileKey);
        InputStream inputStream = ossObject.getObjectContent();
        ByteArrayOutputStream bou = new ByteArrayOutputStream();
        int x = 0;
        while ((x = inputStream.read()) != -1) {
            bou.write(x);
        }
        ossObject.close();
        // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
        // 关闭OSSClient
        ossClient.shutdown();
        return bou.toByteArray();
    }

    @Override
    public void deleteFile(OSS ossClient, String bucketName, String fileKey) throws Exception {
        ossClient.deleteObject(bucketName, fileKey);
        ossClient.shutdown();
    }
}
