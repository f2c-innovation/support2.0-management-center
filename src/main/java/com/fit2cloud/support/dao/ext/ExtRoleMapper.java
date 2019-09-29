package com.fit2cloud.support.dao.ext;

import com.fit2cloud.commons.server.base.domain.Role;
import com.fit2cloud.support.dto.RoleDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Author: chunxing
 * Date: 2018/5/22  下午2:25
 * Description:
 */
public interface ExtRoleMapper {
    List<RoleDTO> paging(@Param("map") Map<String, Object> map);

    List<Role> getRolesByResourceIds(Map<String, Object> param);
}
