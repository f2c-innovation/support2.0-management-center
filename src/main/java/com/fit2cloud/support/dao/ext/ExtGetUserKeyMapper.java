package com.fit2cloud.support.dao.ext;

import com.fit2cloud.commons.server.base.domain.User;
import com.fit2cloud.support.dto.vo.UserKeyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author: maguohao
 * Date: 2019/5/20  下午12:50
 * Description:
 */
public interface ExtGetUserKeyMapper {

    List<User> getUserList(String name);

    List<UserKeyVo> getUserKey(@Param("name") String name);
}
