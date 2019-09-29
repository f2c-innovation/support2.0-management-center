package com.fit2cloud.support.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 */
@PropertySource(value = {
        "file:/opt/fit2cloud/conf/support.properties",
        "classpath:properties/global.properties",
        "classpath:properties/quartz.properties"
}, encoding = "UTF-8", ignoreResourceNotFound = true)
@Configuration
public class CommonConfig {

}
