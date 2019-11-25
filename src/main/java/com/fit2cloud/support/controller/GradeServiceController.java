package com.fit2cloud.support.controller;

import com.fit2cloud.commons.utils.LogUtil;
import com.fit2cloud.commons.utils.PageUtils;
import com.fit2cloud.commons.utils.Pager;
import com.fit2cloud.commons.utils.ResultHolder;
import com.fit2cloud.support.common.constants.PermissionConstants;
import com.fit2cloud.support.dto.CompanyDTO;
import com.fit2cloud.support.model.GradeService;
import com.fit2cloud.support.service.GradeServiceService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 服务等级
 * @author mgh
 * @date 2019/11/12
 */
@RequestMapping("/gradeService")
@RestController
@Api(tags = "服务等级")
public class GradeServiceController {

    @Resource
    private GradeServiceService gradeServiceService;

    @ApiOperation(value = "公司列表")
    @PostMapping(value = "/list/{goPage}/{pageSize}")
    @RequiresPermissions(PermissionConstants.GRADEERVICE_READ)
    public Object paging(@PathVariable int goPage, @PathVariable int pageSize) {
        try{
            Page page = PageHelper.startPage(goPage, pageSize, true);
            return PageUtils.setPageInfo(page, gradeServiceService.getGradeServiceList());
        }catch (Exception e){
            LogUtil.error(e.getMessage());
            return ResultHolder.error(e.getMessage());
        }
    }


}
