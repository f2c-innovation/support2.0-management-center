package com.fit2cloud.support.dao.ext;

import com.fit2cloud.commons.server.base.domain.Department;
import com.fit2cloud.commons.server.model.TreeNode;
import com.fit2cloud.commons.server.model.UserDTO;
import com.fit2cloud.support.dto.DepartmentDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExtDepartmentMapper {
    List<DepartmentDTO> paging(@Param("map") Map<String, Object> map);

    List<Department> linkDepartmentPaging(@Param("companyId") String companyId);

    List<UserDTO> linkCompanyAdminPaging(@Param("companyId") String companyId);

    List<UserDTO> authorizeUsersPaging(@Param("deptId") String deptId);

    List<TreeNode> selectDepartmentTreeNode(@Param("userId") String userId, @Param("roleId") String roleId, @Param("companyId") String companyId);
}
