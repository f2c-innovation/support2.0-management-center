package com.fit2cloud.support.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesConfigurer extends PropertyPlaceholderConfigurer {
    private static Map<String, String> properties = new HashMap();

    public PropertiesConfigurer() {
    }

    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        props.keySet().forEach((key) -> {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            properties.put(keyStr, value);
        });
    }

    public static String getProperty(String key) {
        return getProperty(key, (String)null);
    }

    public static String getProperty(String key, String defaultValue) {
        String value = (String)properties.get(key);
        if (value == null) {
            value = defaultValue;
        }

        return value;
    }

    public static String getString(String key) {
        return getString(key, (String)null);
    }

    public static String getString(String key, String defaultValue) {
        String value = (String)properties.get(key);
        return value == null ? defaultValue : value;
    }

    public static Integer getInteger(String key) {
        return getInteger(key, (Integer)null);
    }

    public static Integer getInteger(String key, Integer defaultValue) {
        String value = (String)properties.get(key);
        return value == null ? defaultValue : Integer.valueOf(value);
    }

    public static Long getLong(String key) {
        return getLong(key, (Long)null);
    }

    public static Long getLong(String key, Long defaultValue) {
        String value = (String)properties.get(key);
        return value == null ? defaultValue : Long.valueOf(value);
    }

    public static Boolean getBoolean(String key) {
        return getBoolean(key, (Boolean)null);
    }

    public static Boolean getBoolean(String key, Boolean defaultValue) {
        String value = (String)properties.get(key);
        return value == null ? defaultValue : Boolean.valueOf(value);
    }
}
