package com.fit2cloud.support.service.uploadfile;

import com.aliyun.oss.OSS;
import com.fit2cloud.support.common.constants.RepositoryConst;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface OperationFileService {

    RepositoryConst getObjectStorageType();

    Boolean saveFile(OSS ossClient, String bucketName, InputStream inputStream, String fileKey) throws Exception;

    URL getFileUrl(OSS ossClient, String bucketName, String filePath);

    byte[] downFile(OSS ossClient, String bucketName, String fileKey) throws IOException;

    void deleteFile(OSS ossClient, String bucketName, String fileKey) throws Exception;

}
