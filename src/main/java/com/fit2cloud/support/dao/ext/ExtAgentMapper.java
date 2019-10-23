package com.fit2cloud.support.dao.ext;

import com.fit2cloud.support.dto.AgentDTO;
import com.fit2cloud.support.dto.request.AgentRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExtAgentMapper {

     List<AgentDTO> selectBySearch(AgentRequest request);

     void deleteByAgentId(@Param("agentId") String agentId);

}
