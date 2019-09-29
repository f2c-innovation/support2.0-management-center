package com.fit2cloud.support.service.uploadfile;

import com.fit2cloud.support.common.constants.RepositoryConst;
import com.fit2cloud.support.service.uploadfile.impl.OSSSupportFileServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class OperationFileFactory {

    private static Map<RepositoryConst, OperationFileService> ossServiceImplMap;

    /**
     * 初始化所有的对象存储service
     *
     * @return
     */
    @PostConstruct
    public void initOperationFileService() {
        ossServiceImplMap = new HashMap<>();
        /**
         * oss
         */
        OperationFileService operationFileService = new OSSSupportFileServiceImpl();
        ossServiceImplMap.put(RepositoryConst.OSS, operationFileService);
        /**
         * 依次扩展s3
         */
    }

}
