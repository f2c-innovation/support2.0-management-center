package com.fit2cloud.support.controller;

import com.fit2cloud.commons.server.base.domain.Agent;
import com.fit2cloud.commons.server.base.domain.Company;
import com.fit2cloud.commons.utils.PageUtils;
import com.fit2cloud.commons.utils.Pager;
import com.fit2cloud.support.common.constants.PermissionConstants;
import com.fit2cloud.support.dto.AgentDTO;
import com.fit2cloud.support.dto.request.AgentRequest;
import com.fit2cloud.support.dto.request.UpdateCompanyRequest;
import com.fit2cloud.support.service.AgentService;
import com.fit2cloud.support.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 代理商
 * @author mgh
 * @date 2019/10/23
 */
@RequestMapping("/agent")
@RestController
@Api(tags = "代理商")
public class AgentController {

    @Resource
    private UserService userService;
    @Resource
    private AgentService agentService;

    /**
     * 代理商查询
     * 代理商查询
     * @param goPage
     * @param pageSize
     * @param request
     * @return
     */
    @ApiOperation(value = "代理商列表")
    @PostMapping(value = "/{goPage}/{pageSize}")
    @RequiresPermissions(PermissionConstants.AGENT_READ)
    public Pager<List<AgentDTO>> paging(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody AgentRequest request) {
        Page page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, agentService.selectBySearch(request));
    }

    @ApiOperation(value = "添加代理商")
    @PostMapping("/add")
    @RequiresPermissions(PermissionConstants.AGENT_CREATE)
    public Agent insert(@RequestBody Agent agent) {
        return agentService.insert(agent);
    }

    @ApiOperation(value = "编辑代理商")
    @PostMapping("/update")
    @RequiresPermissions(PermissionConstants.AGENT_EDIT)
    public Agent update(@RequestBody Agent agent) {
        return agentService.update(agent);
    }

    @ApiOperation(value = "删除代理商")
    @GetMapping(value = "/delete/{agentId}")
    @RequiresPermissions(PermissionConstants.AGENT_DELETE)
    public void delete(@PathVariable String agentId) {
        agentService.delete(agentId);
    }

}
