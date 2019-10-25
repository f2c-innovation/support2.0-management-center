package com.fit2cloud.support.service;

import com.fit2cloud.commons.server.base.domain.Agent;
import com.fit2cloud.commons.server.base.mapper.AgentMapper;
import com.fit2cloud.commons.server.exception.F2CException;
import com.fit2cloud.commons.utils.UUIDUtil;
import com.fit2cloud.support.common.constants.MessageConstants;
import com.fit2cloud.support.dao.ext.ExtAgentMapper;
import com.fit2cloud.support.dto.AgentDTO;
import com.fit2cloud.support.dto.request.AgentRequest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AgentService {

    @Resource
    private ExtAgentMapper extAgentMapper;

    @Resource
    private AgentMapper agentMapper;

    public List<AgentDTO> selectBySearch(AgentRequest request) {
        List<AgentDTO> agentDtoList = extAgentMapper.selectBySearch(request);
        //代理公司
//        for (AgentDTO agentDto : agentDtoList){
//            List<AgentCompanyDto> agentCompanyDtoList =  agentDto.getAgentCompanyDtoList();
//            for (AgentCompanyDto agentCompanyDto:agentCompanyDtoList) {
//                agentDto.getCompanyIdList().add(agentCompanyDto.getCompanyId());
//            }
//        }
//        //代理用户
//        for(AgentDTO agentDto : agentDtoList){
//            List<AgentUserDto> agentUserDtoList = agentDto.getAgentUserDtoList();
//            for (AgentUserDto agentUserDto : agentUserDtoList){
//                agentDto.getUserIdList().add(agentUserDto.getUserId());
//            }
//        }
//        //代理订阅
//        for(AgentDTO agentDto : agentDtoList){
//            List<AgentSubscriptionDto> agentSubscriptionDtoList =  agentDto.getAgentSubscriptionDtoList();
//            for (AgentSubscriptionDto agentSubscriptionDto: agentSubscriptionDtoList){
//                agentDto.getSubscriptionIdList().add(agentSubscriptionDto.getSubscriptionId());
//            }
//        }
        return agentDtoList;
    }

    public Agent insert(Agent agent) {
        try {
            agent.setId(UUIDUtil.newUUID());
            agent.setCreateTime(System.currentTimeMillis());
            agent.setIsDelete(false);
            agentMapper.insertSelective(agent);
        } catch (Exception e) {
            F2CException.throwException(e.getMessage());
        }
        return agent;
    }

    public Agent update(Agent agent) {
        try {
            agentMapper.updateByPrimaryKeySelective(agent);
        } catch (Exception e) {
            F2CException.throwException(e.getMessage());
        }
        return agent;
    }

    public void delete(String id) {
        try {
            agentMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            F2CException.throwException(e.getMessage());
        }
    }
}
