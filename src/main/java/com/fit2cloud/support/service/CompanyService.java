package com.fit2cloud.support.service;

import com.fit2cloud.commons.server.base.domain.Company;
import com.fit2cloud.commons.server.base.domain.CompanyExample;
import com.fit2cloud.commons.server.base.domain.Department;
import com.fit2cloud.commons.server.base.domain.DepartmentExample;
import com.fit2cloud.commons.server.base.mapper.CompanyMapper;
import com.fit2cloud.commons.server.base.mapper.DepartmentMapper;
import com.fit2cloud.commons.server.constants.ResourceOperation;
import com.fit2cloud.commons.server.constants.ResourceTypeConstants;
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
import com.fit2cloud.support.dao.ext.ExtCompanyMapper;
import com.fit2cloud.support.dao.ext.ExtDepartmentMapper;
import com.fit2cloud.support.dto.CompanyDTO;
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
public class CompanyService {

    @Resource
    private CompanyMapper companyMapper;
    @Resource
    private ExtCompanyMapper extCompanyMapper;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private ExtDepartmentMapper extDepartmentMapper;
    @Autowired
    private UserService userService;

    public Object companies(SessionUser sessionUser) {
        CompanyExample example = new CompanyExample();
        if (StringUtils.equals(sessionUser.getParentRoleId(), RoleConstants.Id.CompanyADMIN.name())) {
            Set<String> resourceIds = UserRoleUtils.getResourceIds(sessionUser.getId());
            example.createCriteria().andIdIn(new ArrayList<>(resourceIds));
        }
        example.setOrderByClause("name");
        return companyMapper.selectByExample(example);
    }

    public List<CompanyDTO> paging(Map<String, Object> map) {
        return extCompanyMapper.paging(map);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(List<String> companyIds) {
        DepartmentExample countExample = new DepartmentExample();
        countExample.createCriteria().andCompanyIdIn(companyIds);
        long countWorkspace = departmentMapper.countByExample(countExample);
        if (countWorkspace > 0) {
            throw new RuntimeException("请删除关联部门!");
        }
        companyIds.forEach(companyId -> {
            companyMapper.deleteByPrimaryKey(companyId);
            OperationLogService.log(null, null, SessionUtils.getUser().getId(), SessionUtils.getUser().getName(), ResourceOperation.DELETE, null);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public Company insert(CreateCompanyRequest request) {
        if (StringUtils.isBlank(request.getName())) {
            F2CException.throwException("公司名称不能为空");
        }
        Company company = new Company();
        BeanUtils.copyBean(company, request);
        company.setId(UUIDUtil.newUUID());
        company.setCreateTime(Instant.now().toEpochMilli());
        try {
            companyMapper.insert(company);
            OperationLogService.log(null, null, SessionUtils.getUser().getId(), SessionUtils.getUser().getName(), ResourceOperation.CREATE, null);
        } catch (DuplicateKeyException e) {
            F2CException.throwException(MessageConstants.NameDuplicateKey);
        }
        return company;
    }

    @Transactional(rollbackFor = Exception.class)
    public Company update(UpdateCompanyRequest request) {
        if (StringUtils.isBlank(request.getId())) {
            F2CException.throwException("ID不能为空");
        }
        if (StringUtils.isBlank(request.getName())) {
            F2CException.throwException("公司名称不能为空");
        }
        Company company = new Company();
        BeanUtils.copyBean(company, request);
        try {
            companyMapper.updateByPrimaryKeySelective(company);
            OperationLogService.log(null, null, SessionUtils.getUser().getId(), SessionUtils.getUser().getName(), ResourceOperation.UPDATE, null);
        } catch (DuplicateKeyException e) {
            F2CException.throwException(MessageConstants.NameDuplicateKey);
        }

        return company;
    }

    public List<Department> linkDepartmentPaging(String companyId) {
        return extDepartmentMapper.linkDepartmentPaging(companyId);
    }

    public List<UserDTO> linkCompanyAdminPaging(String companyId) {
        List<UserDTO> list = extDepartmentMapper.linkCompanyAdminPaging(companyId);
        userService.convertUserDTO(list, new HashMap<>());
        return list;
    }

    public Object currentCompany(String companyId) {
        Company company = companyMapper.selectByPrimaryKey(companyId);
        return Collections.singletonList(company);
    }
}
