package com.fit2cloud.support.controller;

import com.fit2cloud.commons.server.base.domain.Category;
import com.fit2cloud.commons.server.base.domain.Dictionary;
import com.fit2cloud.commons.utils.LogUtil;
import com.fit2cloud.commons.utils.PageUtils;
import com.fit2cloud.commons.utils.Pager;
import com.fit2cloud.commons.utils.ResultHolder;
import com.fit2cloud.support.common.constants.PermissionConstants;
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

    @ApiOperation(value = "添加一级字典")
    @PostMapping(value = "/category/add")
    @RequiresPermissions(PermissionConstants.DICTIONARY_CREATE)
    public Object addCategory(@RequestBody Category category) {
        try {
            return ResultHolder.success(dictionaryService.addCategory(category));
        }catch (Exception e){
            LogUtil.error(e.getMessage());
            return ResultHolder.error(e.getMessage());
        }
    }

    @ApiOperation(value = "编辑一级字典")
    @PostMapping(value = "/category/update")
    @RequiresPermissions(PermissionConstants.DICTIONARY_EDIT)
    public Object updateCategory(@RequestBody Category category) {
        try {
            return ResultHolder.success(dictionaryService.updateCategory(category));
        }catch (Exception e){
            LogUtil.error(e.getMessage());
            return ResultHolder.error(e.getMessage());
        }
    }

    @ApiOperation(value = "编辑一级字典")
    @PostMapping(value = "/category/update/status")
    @RequiresPermissions(PermissionConstants.DICTIONARY_EDIT)
    public Object updateCategoryStatus(@RequestBody Category category) {
        try {
            return ResultHolder.success(dictionaryService.updateCategory(category));
        }catch (Exception e){
            LogUtil.error(e.getMessage());
            return ResultHolder.error(e.getMessage());
        }
    }

    @ApiOperation(value = "删除一级字典")
    @GetMapping(value = "/category/delete/{id}")
    @RequiresPermissions(PermissionConstants.DICTIONARY_DELETE)
    public Object deleteCategory(@PathVariable String id) {
        try {
            return ResultHolder.success(dictionaryService.deleteCategory(id));
        }catch (Exception e){
            LogUtil.error(e.getMessage());
            return ResultHolder.error(e.getMessage());
        }
    }

    @ApiOperation(value = "删除二级字典")
    @GetMapping(value = "/dictionary/delete/{id}")
    @RequiresPermissions(PermissionConstants.DICTIONARY_DELETE)
    public Object deleteDictionary(@PathVariable String id) {
        try {
            return ResultHolder.success(dictionaryService.deleteDictionary(id));
        }catch (Exception e){
            LogUtil.error(e.getMessage());
            return ResultHolder.error(e.getMessage());
        }
    }

}
