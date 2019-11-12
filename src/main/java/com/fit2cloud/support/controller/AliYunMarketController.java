package com.fit2cloud.support.controller;

import com.fit2cloud.support.dto.response.CreateInstanceResponse;
import com.fit2cloud.support.service.AliyunMarketRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 阿里云订阅渠道
 * @author maguohao
 * @date 2019/11/12
 */
@RestController
@RequestMapping(value = "/aliMarket")
@Slf4j
public class AliYunMarketController {

    @Resource
    private AliyunMarketRecordService aliyunMarketRecordService;

//    @RequestMapping(value = "", params = "action=createInstance")
//    public Object createInstance(HttpServletRequest request, AliyunMarketRecord aliyunMarketRecord) {
//        try {
//            return aliyunMarketRecordService.createInstance(request, aliyunMarketRecord);
//        } catch (Exception e) {
//            log.error("阿里云用户创建客户支持账户失败:{}",e);
//            return CreateInstanceResponse.getFail();
//        }
//    }
//
//    //续费实例
//    @RequestMapping(value = "", params = "action=renewInstance")
//    public Object renewInstance(HttpServletRequest request, AliyunMarketRecord aliyunMarketRecord) {
//        try {
//            return aliyunMarketRecordService.renewInstance(request, aliyunMarketRecord);
//        } catch (Exception e) {
//            log.error("阿里云用户客户续费失败:{}",e);
//            return CreateInstanceResponse.getFail();
//        }
//    }
}
