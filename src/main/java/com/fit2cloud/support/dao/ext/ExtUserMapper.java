package com.fit2cloud.support.dao.ext;

import com.fit2cloud.commons.server.base.domain.User;
import com.fit2cloud.commons.server.base.domain.Workspace;
import com.fit2cloud.commons.server.model.UserDTO;
import com.fit2cloud.support.dto.RoleInfo;
import com.fit2cloud.support.dto.UserKeysDTO;
import com.fit2cloud.support.dto.UserOperateDTO;
import com.fit2cloud.support.dto.vo.UserKeyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: maguohao
 * Date: 2019/5/20  下午12:50
 * Description:
 */
public interface ExtUserMapper {

    List<UserDTO> paging(@Param("map") Map<String, Object> map);

    Long countActivesUser(@Param("roleName") String roleName);

    List<RoleInfo> roleInfo(Map<String, Object> param);

    List<Workspace> resourcePaging(@Param("resourceType") String resourceType, @Param("userId") String userId, @Param("roleId") String roleId);

    Set<String> getIds();

    Integer selectByPrimaryKey(String name);

    Integer updateUser(UserOperateDTO userOperateDto);

    List<User> getUserList(String name);

    List<UserKeysDTO> getUserKeysDTOList();

    List<UserKeyVo> getUserKey(@Param("name") String name);
}
