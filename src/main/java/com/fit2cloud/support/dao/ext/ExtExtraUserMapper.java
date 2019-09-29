package com.fit2cloud.support.dao.ext;

import com.fit2cloud.commons.server.base.domain.ExtraUser;
import com.fit2cloud.support.model.ExtraUserLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Author: maguohao
 * Date: 2019/5/28  下午2:07
 * Description:
 */
public interface ExtExtraUserMapper {
    List<ExtraUser> paging(@Param("map") Map<String, Object> map);

    List<ExtraUserLog> usersLog(@Param("map") Map<String, Object> map);

}
