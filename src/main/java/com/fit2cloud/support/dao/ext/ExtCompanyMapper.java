package com.fit2cloud.support.dao.ext;

import com.fit2cloud.support.dto.CompanyDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExtCompanyMapper {
    List<CompanyDTO> paging(@Param("map") Map<String, Object> map);
}
