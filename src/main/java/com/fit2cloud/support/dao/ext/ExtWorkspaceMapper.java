package com.fit2cloud.support.dao.ext;

import com.fit2cloud.commons.server.base.domain.Workspace;
import com.fit2cloud.commons.server.model.TreeNode;
import com.fit2cloud.commons.server.model.UserDTO;
import com.fit2cloud.support.dto.WorkspaceDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Author: chunxing
 * Date: 2018/5/28  下午4:49
 * Description:
 */
public interface ExtWorkspaceMapper {
    List<WorkspaceDTO> paging(@Param("map") Map<String, Object> map);

    List<Workspace> linkWorkspacePaging(@Param("organizationId") String organizationId);

    List<UserDTO> linkOrgAdminPaging(@Param("organizationId") String organizationId);

    List<UserDTO> authorizeUsersPaging(@Param("workspaceId") String workspaceId);

    List<TreeNode> selectWorkspaceTreeNode(@Param("userId") String userId, @Param("roleId") String roleId, @Param("organizationId") String organizationId);
}
