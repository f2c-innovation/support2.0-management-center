package com.fit2cloud.support.service;

import com.alibaba.fastjson.JSON;
import com.fit2cloud.commons.server.base.domain.FileStore;
import com.fit2cloud.commons.server.base.domain.SystemParameter;
import com.fit2cloud.commons.server.base.domain.SystemParameterExample;
import com.fit2cloud.commons.server.base.mapper.FileStoreMapper;
import com.fit2cloud.commons.server.base.mapper.SystemParameterMapper;
import com.fit2cloud.commons.server.constants.ParamConstants;
import com.fit2cloud.commons.server.exception.F2CException;
import com.fit2cloud.commons.utils.BeanUtils;
import com.fit2cloud.commons.utils.EncryptUtils;
import com.fit2cloud.commons.utils.UUIDUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class SystemParameterService {

    @Resource
    private SystemParameterMapper parameterMapper;
    @Resource
    private Environment environment;
    @Resource
    private FileStoreMapper fileStoreMapper;


    public String getValue(String key) {
        SystemParameter systemParameter = parameterMapper.selectByPrimaryKey(key);
        if (systemParameter == null) {
            return null;
        }
        return systemParameter.getParamValue();
    }

    public List<SystemParameter> getParamList(String type) {
        SystemParameterExample example = new SystemParameterExample();
        example.createCriteria().andParamKeyLike(type + "%");
        return parameterMapper.selectByExample(example);
    }

    public Object keyCloakInfo(String type) {
        String address = environment.getProperty(ParamConstants.KeyCloak.AUTH_SERVER_URL.getValue());
        List<SystemParameter> paramList = this.getParamList(type);
        for (SystemParameter parameter : paramList) {
            if (StringUtils.equalsIgnoreCase(ParamConstants.Type.PASSWORD.getValue(), parameter.getType())) {
                parameter.setParamValue(EncryptUtils.aesDecrypt(parameter.getParamValue()).toString());
            }
        }
        paramList.sort(Comparator.comparing(SystemParameter::getParamKey).reversed());
        SystemParameter parameter = new SystemParameter();
        parameter.setParamKey(ParamConstants.KeyCloak.ADDRESS.getValue());
        parameter.setParamValue(address);
        paramList.add(parameter);
        return paramList;
    }

    public void editKeyCloakInfo(List<SystemParameter> parameters) {
        for (SystemParameter parameter : parameters) {
            if (StringUtils.equalsIgnoreCase(ParamConstants.Type.PASSWORD.getValue(), parameter.getType())) {
                String string = EncryptUtils.aesEncrypt(parameter.getParamValue()).toString();
                parameter.setParamValue(string);
            }
            parameterMapper.updateByPrimaryKey(parameter);
        }
    }

    public Object uiInfo(String type) throws InvocationTargetException, IllegalAccessException {
        List<SystemParameter> paramList = this.getParamList(type);
        List<SystemParameterDTO> dtoList = new ArrayList<>();
        for (SystemParameter systemParameter : paramList) {
            SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
            BeanUtils.copyBean(systemParameterDTO, systemParameter);
            if (systemParameter.getType().equalsIgnoreCase("file")) {
                FileStore fileStore = fileStoreMapper.selectByPrimaryKey(systemParameterDTO.getParamValue());
                if (fileStore != null) {
                    systemParameterDTO.setFileName(fileStore.getName());
                }
            }
            dtoList.add(systemParameterDTO);
        }
        dtoList.sort(Comparator.comparingInt(SystemParameter::getSort));
        return dtoList;
    }

    public Object mailInfo(String type) {
        List<SystemParameter> paramList = this.getParamList(type);
        if (CollectionUtils.isEmpty(paramList)) {
            paramList = new ArrayList<>();
            ParamConstants.MAIL[] values = ParamConstants.MAIL.values();
            for (ParamConstants.MAIL value : values) {
                SystemParameter systemParameter = new SystemParameter();
                if (value.equals(ParamConstants.MAIL.PASSWORD)) {
                    systemParameter.setType(ParamConstants.Type.PASSWORD.getValue());
                } else {
                    systemParameter.setType(ParamConstants.Type.TEXT.getValue());
                }
                systemParameter.setParamKey(value.getKey());
                systemParameter.setSort(value.getValue());
                paramList.add(systemParameter);
            }
        } else {
            paramList.stream().filter(param -> param.getParamKey().equals(ParamConstants.MAIL.PASSWORD.getKey())).forEach(param -> {
                String string = EncryptUtils.aesDecrypt(param.getParamValue()).toString();
                param.setParamValue(string);
            });
        }
        paramList.sort(Comparator.comparingInt(SystemParameter::getSort));
        return paramList;
    }

    public void editMailInfo(List<SystemParameter> parameters) {
        List<SystemParameter> paramList = this.getParamList(ParamConstants.Classify.MAIL.getValue());
        boolean empty = paramList.size() < 2;
        parameters.forEach(parameter -> {
            if (parameter.getParamKey().equals(ParamConstants.MAIL.PASSWORD.getKey())) {
                String string = EncryptUtils.aesEncrypt(parameter.getParamValue()).toString();
                parameter.setParamValue(string);
            }
            if (empty) {
                parameterMapper.insert(parameter);
            } else {
                parameterMapper.updateByPrimaryKey(parameter);
            }
        });
    }


    public void editMailInfoAble(SystemParameter systemParameter) {
        List<SystemParameter> paramList = this.getParamList(ParamConstants.Classify.MAIL.getValue());
        if (paramList.size() > 1) {
            parameterMapper.updateByPrimaryKey(systemParameter);
        } else {
            F2CException.throwException("请先补全参数");
        }
    }

    public void testConnection(HashMap<String, String> hashMap) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setDefaultEncoding("UTF-8");
        javaMailSender.setHost(hashMap.get(ParamConstants.MAIL.SERVER.getKey()));
        javaMailSender.setPort(Integer.valueOf(hashMap.get(ParamConstants.MAIL.PORT.getKey())));
        javaMailSender.setUsername(hashMap.get(ParamConstants.MAIL.ACCOUNT.getKey()));
        javaMailSender.setPassword(hashMap.get(ParamConstants.MAIL.PASSWORD.getKey()));
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        if (BooleanUtils.toBoolean(hashMap.get(ParamConstants.MAIL.SSL.getKey()))) {
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }
        if (BooleanUtils.toBoolean(hashMap.get(ParamConstants.MAIL.TLS.getKey()))) {
            props.put("mail.smtp.starttls.enable", "true");
        }
        javaMailSender.setJavaMailProperties(props);
        try {
            javaMailSender.testConnection();
        } catch (MessagingException e) {
            F2CException.throwException(e.getMessage());
        }
    }

    public void saveValue(SystemParameter systemParameter) {
        if (parameterMapper.updateByPrimaryKeySelective(systemParameter) == 0) {
            parameterMapper.insertSelective(systemParameter);
        }
    }

    public void editUiInfo(MultipartFile[] files, String parameter) throws IOException {
        List<SystemParameterDTO> systemParameters = JSON.parseArray(parameter, SystemParameterDTO.class);
        for (MultipartFile multipartFile : files) {
            if (!multipartFile.isEmpty()) {
                String multipartFileName = multipartFile.getOriginalFilename();
                String[] split = Objects.requireNonNull(multipartFileName).split(",");
                systemParameters.stream().filter(systemParameterDTO -> systemParameterDTO.getParamKey().equalsIgnoreCase(split[1])).forEach(systemParameterDTO -> {
                    systemParameterDTO.setFileName(split[0]);
                    systemParameterDTO.setFile(multipartFile);
                });
            }
        }
        for (SystemParameterDTO systemParameter : systemParameters) {
            MultipartFile file = systemParameter.getFile();
            if (systemParameter.getType().equalsIgnoreCase("file")) {
                if (StringUtils.isBlank(systemParameter.getFileName())) {
                    fileStoreMapper.deleteByPrimaryKey(systemParameter.getParamValue());
                }
                if (file != null) {
                    fileStoreMapper.deleteByPrimaryKey(systemParameter.getParamValue());
                    String uuid = UUIDUtil.newUUID();
                    FileStore fileStore = new FileStore();
                    fileStore.setId(uuid);
                    fileStore.setName(systemParameter.getFileName());
                    fileStore.setFile(file.getBytes());
                    fileStore.setSize(file.getSize());
                    fileStore.setCreateTime(Instant.now().toEpochMilli());
                    fileStoreMapper.insert(fileStore);
                    systemParameter.setParamValue(uuid);
                }
                if (file == null && systemParameter.getFileName() == null) {
                    systemParameter.setParamValue(null);
                }
            }
            parameterMapper.deleteByPrimaryKey(systemParameter.getParamKey());
            parameterMapper.insert(systemParameter);
        }

    }
}
