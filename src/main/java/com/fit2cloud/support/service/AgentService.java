package com.fit2cloud.support.service;

import com.fit2cloud.commons.server.base.domain.Company;
import com.fit2cloud.commons.server.base.domain.CompanyExample;
import com.fit2cloud.commons.server.base.domain.Department;
import com.fit2cloud.commons.server.base.domain.DepartmentExample;
import com.fit2cloud.commons.server.base.mapper.CompanyMapper;
import com.fit2cloud.commons.server.base.mapper.DepartmentMapper;
import com.fit2cloud.commons.server.constants.ResourceOperation;
import com.fit2cloud.commons.server.constants.RoleConstants;
import com.fit2cloud.commons.server.exception.F2CException;
import com.fit2cloud.commons.server.model.SessionUser;
import com.fit2cloud.commons.server.model.UserDTO;
import com.fit2cloud.commons.server.service.OperationLogService;
import com.fit2cloud.commons.server.utils.SessionUtils;
import com.fit2cloud.commons.server.utils.UserRoleUtils;
import com.fit2cloud.commons.utils.BeanUtils;
import com.fit2cloud.commons.utils.UUIDUtil;
import com.fit2cloud.support.common.constants.MessageConstants;
import com.fit2cloud.support.dao.ext.ExtAgentMapper;
import com.fit2cloud.support.dao.ext.ExtCompanyMapper;
import com.fit2cloud.support.dao.ext.ExtDepartmentMapper;
import com.fit2cloud.support.dto.AgentDTO;
import com.fit2cloud.support.dto.CompanyDTO;
import com.fit2cloud.support.dto.request.AgentRequest;
import com.fit2cloud.support.dto.request.CreateCompanyRequest;
import com.fit2cloud.support.dto.request.UpdateCompanyRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class AgentService {

    @Resource
    private ExtAgentMapper extAgentMapper;

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
}
