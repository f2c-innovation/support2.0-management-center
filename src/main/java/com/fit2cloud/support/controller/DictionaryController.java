package com.fit2cloud.support.controller;

import com.fit2cloud.commons.server.base.domain.Category;
import com.fit2cloud.commons.server.base.domain.Dictionary;
import com.fit2cloud.commons.utils.PageUtils;
import com.fit2cloud.commons.utils.Pager;
import com.fit2cloud.support.common.constants.PermissionConstants;
import com.fit2cloud.support.dto.CompanyDTO;
import com.fit2cloud.support.dto.request.CompanyRequest;
import com.fit2cloud.support.service.DictionaryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value="/dictionary")
@Api(tags = "字典")
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    @ApiOperation(value = "一级列表")
    @PostMapping(value = "/category/{goPage}/{pageSize}")
    @RequiresPermissions(PermissionConstants.DICTIONARY_READ)
    public Pager<List<Category>> getCategoryList(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody Category request) {
        Page page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, dictionaryService.getCategoryList(request));
    }

    @ApiOperation(value = "二级列表")
    @PostMapping(value = "/dictionary/{goPage}/{pageSize}")
    @RequiresPermissions(PermissionConstants.DICTIONARY_READ)
    public Pager<List<Dictionary>> getDictionaryList(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody Dictionary request) {
        Page page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, dictionaryService.getDictionaryList(request));
    }

}
