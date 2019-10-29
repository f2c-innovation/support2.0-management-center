package com.fit2cloud.support.dao.ext;

import com.fit2cloud.support.dto.AgentCompanyDTO;
import com.fit2cloud.support.model.Agent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtAgentCompanyMapper {

    List<AgentCompanyDTO> selectByAgentId(@Param("agentId") String agentId);

    List<AgentCompanyDTO> selectByCompanyId(@Param("companyId") String companyId);

    List<Agent> getAgentListByCompanyId(@Param("companyId") String companyId);

    void deleteByAgentId(@Param("agentId") String agentId);

    void deleteByCompanyId(@Param("companyId") String companyId);

    int getCompanyCountByAgentId(@Param("agentId") String agentId);
}
