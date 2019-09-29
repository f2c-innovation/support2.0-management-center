package com.fit2cloud.support.config;

import com.fit2cloud.commons.server.config.DBEncryptConfig;
import com.fit2cloud.commons.utils.EncryptConfig;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomDBEncryptConfig implements DBEncryptConfig {

    public List<EncryptConfig> encryptConfig() {
        List<EncryptConfig> configs = new ArrayList<>();
        configs.add(new EncryptConfig("com.fit2cloud.config.dto.vo.UserKeyVo", "secretKey"));
        return configs;
    }

}
