package com.fit2cloud.support.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fit2cloud.commons.server.base.domain.*;
import com.fit2cloud.commons.server.base.mapper.*;
import com.fit2cloud.commons.server.constants.ParamConstants;
import com.fit2cloud.commons.server.exception.F2CException;
import com.fit2cloud.commons.utils.CommonThreadPool;
import com.fit2cloud.commons.utils.EncryptUtils;
import com.fit2cloud.commons.utils.LogUtil;
import com.fit2cloud.commons.utils.UUIDUtil;
import com.fit2cloud.support.common.constants.UserConstants;
import com.fit2cloud.support.common.constants.utils.MD5Util;
import com.fit2cloud.support.dao.ExtraDataLogMapper;
import com.fit2cloud.support.dao.ExtraUserLogMapper;
import com.fit2cloud.support.dao.ext.ExtExtraUserLogMapper;
import com.fit2cloud.support.dao.ext.ExtExtraUserMapper;
import com.fit2cloud.support.dao.ext.ExtUserMapper;
import com.fit2cloud.support.dto.RoleInfo;
import com.fit2cloud.support.dto.UserOperateDTO;
import com.fit2cloud.support.dto.keycloakToken;
import com.fit2cloud.support.dto.keycloakUser;
import com.fit2cloud.support.dto.vo.UserAddVo;
import com.fit2cloud.support.model.ExtraDataLog;
import com.fit2cloud.support.model.ExtraDataLogExample;
import com.fit2cloud.support.model.ExtraUserLog;
import com.fit2cloud.support.model.ExtraUserLogExample;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ExtraUserService {

    @Resource(name = "remoteRestTemplate")
    private RestTemplate remoteRestTemplate;
    @Resource
    private ExtUserMapper extUserMapper;
    @Resource
    private ExtraUserMapper extraUserMapper;
    @Resource
    private Environment environment;
    @Resource
    private SystemParameterService systemParameterService;
    @Resource
    private ExtExtraUserMapper extExtraUserMapper;
    @Resource
    private CommonThreadPool commonThreadPool;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired(required=false)
    private DefaultRedisScript<Boolean> distributedLockScript;
    @Autowired
    private UserService userService;
    @Resource
    private ExtraDataLogMapper extraDataLogMapper;
    @Resource
    private WorkspaceMapper workspaceMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private ExtExtraUserLogMapper extExtraUserLogMapper;
    @Resource
    private ExtraUserLogMapper extraUserLogMapper;
    @Resource
    private RoleMapper roleMapper;

    /**
     * https://www.keycloak.org/docs/latest/server_development/index.html
     * https://www.keycloak.org/docs-api/4.0/rest-api/index.html
     */
    public void syncExtraUser(Boolean isReload) {
        String username = systemParameterService.getValue(ParamConstants.KeyCloak.USERNAME.getValue());
        String aesPassword = systemParameterService.getValue(ParamConstants.KeyCloak.PASSWORD.getValue());
        if (StringUtils.isBlank(username) || StringUtils.isBlank(aesPassword)) {
            F2CException.throwException("请先设置 KeyCloak用户名和密码！");
        }
        if (isReload) {
            this.sync(true, username, aesPassword);
        } else {
            Boolean execute = stringRedisTemplate.execute(distributedLockScript, Collections.singletonList("syncExtraUser"), "lock", String.valueOf(20));
            if (execute) {
                this.sync(isReload, username, aesPassword);
            }
        }

    }

    public void sync(Boolean isReload, String username, String aesPassword) {
        commonThreadPool.addTask(() -> {

            List<ExtraUser> list = new LinkedList<>();//添加到log里的人
            try {
                LogUtil.info("开始同步用户");
                AtomicInteger sum = new AtomicInteger();
                String password = EncryptUtils.aesDecrypt(aesPassword).toString();
                String serverAddress = environment.getProperty(ParamConstants.KeyCloak.ADDRESS.getValue());
                String realm = environment.getProperty(ParamConstants.KeyCloak.REALM.getValue());

                //todo 可以先让 keycloak sync users,再获取 user，keycloak 也可以设置定时同步

                //send request
                HttpHeaders userHeaders = new HttpHeaders();
                userHeaders.setAccept(new ArrayList<>(Collections.singleton(MediaType.APPLICATION_JSON)));
                userHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
                List<keycloakUser> keycloakUsers = new ArrayList<>();
                int first = 0;
                int max = 200;
                String userUrl = String.format("%s%s%s%s", serverAddress, "/admin/realms/", realm, "/users");
                List<keycloakUser> page;
                do {
                    page = new ArrayList<>();
                    String token = this.getToken(serverAddress, username, password);
                    userHeaders.set("Authorization", String.format("%s%s", "bearer ", token));
                    HttpEntity<Object> userEntity = new HttpEntity<>(userHeaders);
                    ResponseEntity<String> exchange = remoteRestTemplate.exchange(userUrl.concat("?first=" + first + "&max=" + max), HttpMethod.GET, userEntity, String.class);
                    page = JSON.parseArray(exchange.getBody(), keycloakUser.class);
                    if (CollectionUtils.isNotEmpty(page)) {
                        keycloakUsers.addAll(page);
                    }
                    LogUtil.debug(page.size() + String.format(" users obtained from this page %s/%s", first, max));
                    first = first + max;
                } while (CollectionUtils.isNotEmpty(page));

                LogUtil.info(keycloakUsers.size() + " users fetched from keycloak.");

                Set<String> ids = new HashSet<>();
                ids.addAll(extUserMapper.getIds());

                if (!isReload) {
                    List<ExtraUser> extraUsers = extraUserMapper.selectByExample(null);
                    extraUsers.forEach(extraUser -> ids.add(extraUser.getName()));
                }
                LogUtil.warn("addLdapUserList start size : " + keycloakUsers.size());

                if(keycloakUsers.size() > 0){
                    for(keycloakUser keycloakUser : keycloakUsers){
                        if (!StringUtils.isBlank(keycloakUser.getUsername()) && !ids.contains(keycloakUser.getUsername())
                            && !StringUtils.isBlank(keycloakUser.getEmail())) {
                            try {
                                LogUtil.warn("keycloakUser: " + ReflectionToStringBuilder.toString(keycloakUser));
                                String displayName = keycloakUser.getAttributes().getDisplayName();
                                String deptShortName = keycloakUser.getAttributes().getDeptShortName();
                                if(displayName.contains("[") && displayName.contains("]")){
                                    String str[] = displayName.split("\"");
                                    displayName = str[1];
                                }
                                if(deptShortName.contains("[") && deptShortName.contains("]")){
                                    String str[] = deptShortName.split("\"");
                                    deptShortName = str[1];
                                }
                                LogUtil.warn("displayName : " + displayName);
                                LogUtil.warn("getBbaShortName : " + keycloakUser.getAttributes().getDeptShortName());
                                ExtraUser extraUser = new ExtraUser();
                                extraUser.setEmail(keycloakUser.getEmail());
                                extraUser.setName(keycloakUser.getUsername() != null ? keycloakUser.getUsername() : keycloakUser.getId());
                                extraUser.setDisplayName(displayName);
                                extraUser.setPhone(extraUser.getPhone());
                                extraUser.setSyncTime(Instant.now().toEpochMilli());
                                extraUser.setType(UserConstants.Source.EXTRA.getValue());

                                Map map = new HashMap();
                                map.put("name", keycloakUser.getUsername() != null ? keycloakUser.getUsername() : keycloakUser.getId());
                                List<ExtraUser> extraUserList = extExtraUserMapper.paging(map);
                                if(extraUserList.size() > 0){
                                    for(ExtraUser e : extraUserList){
                                        extraUser.setId(e.getName());
                                        extraUserMapper.updateByPrimaryKeySelective(extraUser);
                                    }
                                    list.add(extraUser);
                                }else{
                                    extraUser.setId(keycloakUser.getUsername() != null ? keycloakUser.getUsername() : keycloakUser.getId());
                                    extraUserMapper.insert(extraUser);
                                    list.add(extraUser);
                                    sum.getAndIncrement();
                                    ids.add(keycloakUser.getUsername());
                                }
                                LogUtil.warn("extraUser: " + list.size() + " 条记录");
                                //限制每次最多100条数据同步
                                if(list.size() == 100){
                                    this.addExtraDataLog(list);
                                    list.clear();
                                }
                            } catch (Exception e) {
                                LogUtil.error("插入用户失败: " + JSONObject.toJSONString(keycloakUser), e);
                            }
                        }
                    }
                }
                LogUtil.warn("结束同步用户，共同步" + list.size() + "条记录");
            } catch (Exception e) {
                LogUtil.error("同步用户异常", e);
            } finally {
                this.addExtraDataLog(list);//不够1000条数据的时候走finally
            }
        });
    }

    public void addExtraDataLog(List<ExtraUser> list){
        //添加同步用户日志
        if(list.size() > 0){
            try {
                ExtraDataLog extraDataLog = new ExtraDataLog();
                extraDataLog.setId(UUIDUtil.newUUID());
                extraDataLog.setCreateTime(System.currentTimeMillis());
                String dec = "Additional users are: " + "\n";
                for (ExtraUser e : list) {
                    String user =  e.getName() + " (" + e.getEmail() + "); ";
                    dec += user;
                }
                extraDataLog.setDescription(dec);
                int m = extraDataLogMapper.insertSelective(extraDataLog);
                LogUtil.warn("ldapDataLogMapper.insertSelective size: " + m);
                this.addWorkSpaceWithUser(list, "extra");
            }catch(Exception e){
                LogUtil.warn("ldapDataLogMapper error: " + e.getMessage());
            }
        }
    }

    //自动将用户添加到工作空间
    public void addWorkSpaceWithUser(List<ExtraUser> extraUserList, String type) throws Exception{
        LogUtil.warn("addWorkSpaceWithUser ************************ start");
        try {
            for (ExtraUser extraUser : extraUserList) {
                UserAddVo userVo = new UserAddVo();
                String userName = extraUser.getDisplayName();
                LogUtil.warn("addWorkSpaceWithUser ************************111 start: " + userName);

                //查询手动创建的User角色
                RoleExample roleExample = new RoleExample();
                roleExample.createCriteria().andNameEqualTo("User");
                List<Role> roleList = roleMapper.selectByExample(roleExample);

                userVo.setName(extraUser.getName());
                userVo.setEmail(extraUser.getEmail());
                userVo.setRoleId(roleList.get(0).getId());
                userVo.setNewGroup(false);
                userVo.setId(extraUser.getName());

                List<Workspace> workspaceList = workspaceMapper.selectByExample(null);

                boolean flag = false;
                for (Workspace workspace : workspaceList) {
                    userVo.setOrgId(workspace.getId());
                    userVo.setOrgName(workspace.getName());
                    flag = true;
                    break;
                }
                LogUtil.warn("flag : " + flag);
                if (!flag) continue;
                LogUtil.warn("addWorkSpaceWithUser  mm1: ");
                UserExample example = new UserExample();
                example.createCriteria().andIdEqualTo(userVo.getId());
                List<User> users = userMapper.selectByExample(example);
                if (users.size() > 0) continue;
                userVo.setCreateTime(System.currentTimeMillis());
                userVo.setSource(type);
                //null,则是普通用户
                if (userVo.getPassword() == null) {
                    userVo.setPassword(MD5Util.MD5(UUID.randomUUID().toString()));
                } else {
                    userVo.setPassword(MD5Util.MD5(userVo.getPassword()));
                }
                userVo.setLastSourceId(userVo.getOrgId());
                userVo.setActive(true);
                int n = userMapper.insertSelective(userVo);
                LogUtil.warn("addWorkSpaceWithUser mm1.5 size: " + n);
                String userId = userVo.getId();
                LogUtil.warn("addWorkSpaceWithUser  mm2: " + userId);

                UserRole userRole = new UserRole();
                userRole.setSourceId(userVo.getOrgId());
                userRole.setUserId(userId);
                userRole.setRoleId(userVo.getRoleId());
                userRole.setId(UUIDUtil.newUUID());
                int m = userRoleMapper.insert(userRole);
                LogUtil.warn("addWorkSpaceWithUser  mm3 size: " + m + "  id " + userRole.getId());


                LogUtil.warn("addWorkSpaceWithUser  mm4: " + userVo);
                this.syncExtraLog(userVo, "自动");
            }
            LogUtil.warn("addWorkSpaceWithUser ************************ end");
        }catch (Exception e){
            LogUtil.warn("addWorkSpaceWithUser error: " + e.getMessage());
        }
    }

    //导入日志
    public void syncExtraLog(UserAddVo userVo, String type) {
        LogUtil.warn("syncExtraLog  *******  start");
        try {
            ExtraUserLog extraUserLog = new ExtraUserLog();
            extraUserLog.setName(userVo.getName());
            extraUserLog.setEmail(userVo.getEmail());
            extraUserLog.setCreateTime(System.currentTimeMillis());
            extraUserLog.setStatus(type);
            if(userVo.getOrgId() != null){
                extraUserLog.setGroupEnvId(userVo.getOrgId());
                extraUserLog.setGroupEnvName(userVo.getOrgName());
            }
//            extExtraUserLogMapper.delete(userVo.getName());
            ExtraUserLogExample extraUserLogExample = new ExtraUserLogExample();
            extraUserLogExample.createCriteria().andNameEqualTo(userVo.getOrgName());
            List<ExtraUserLog> extraUserList = extraUserLogMapper.selectByExample(extraUserLogExample);
            if(extraUserList.size() == 0){
                extraUserLog.setId(UUIDUtil.newUUID());
                int n = extraUserLogMapper.insertSelective(extraUserLog);
                LogUtil.warn("syncExtraLog  *******  end: " + n + "条记录");
            }
        }catch (Exception e){
            LogUtil.warn("syncExtraLog error：" + e.getMessage());
        }
    }

    private String getToken(String serverAddress, String username, String password) {
        LogUtil.warn("getToken: " + serverAddress + "\n username: " + username + "\n password: " + password);
        //get Token
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setAccept(new ArrayList<>(Collections.singleton(MediaType.APPLICATION_JSON)));
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> mvm = new LinkedMultiValueMap<>();
        mvm.add("client_id", "admin-cli");
        mvm.add("username", username);
        mvm.add("password", password);
        mvm.add("grant_type", "password");
        HttpEntity<Object> formEntity = new HttpEntity<>(mvm, tokenHeaders);
        String tokenUrl = String.format("%s%s", serverAddress, "/realms/master/protocol/openid-connect/token");
        ResponseEntity<keycloakToken> keycloakTokenEntity = remoteRestTemplate.postForEntity(tokenUrl, formEntity, keycloakToken.class);
        LogUtil.warn("keycloakTokenEntity.getBody: " + ReflectionToStringBuilder.toString(keycloakTokenEntity));
        return Objects.requireNonNull(keycloakTokenEntity.getBody()).getAccessToken();
    }

    public List<ExtraUser> paging(Map<String, Object> map) {
        return extExtraUserMapper.paging(map);
    }

    public List<ExtraUserLog> usersLog(Map<String, Object> map){

        return extExtraUserMapper.usersLog(map);
    }

    public List<ExtraDataLog> dataLog(){
        ExtraDataLogExample extraDataLogExample = new ExtraDataLogExample();
        extraDataLogExample.setOrderByClause("create_time desc");
        return extraDataLogMapper.selectByExample(extraDataLogExample);
    }

    public void reloadSync() {
        Boolean execute = stringRedisTemplate.execute(distributedLockScript, Collections.singletonList("syncExtraUser"), "lock", String.valueOf(20));
        if (execute) {
//            extraUserMapper.deleteByExample(null);
            this.syncExtraUser(true);
        }
    }


    public void importUser(List<RoleInfo> roleInfoList, List<String> ids) {
        ExtraUserExample extraUserExample = new ExtraUserExample();
        extraUserExample.createCriteria().andIdIn(ids);
        List<ExtraUser> extraUsers = extraUserMapper.selectByExample(extraUserExample);
        boolean termination = false;
        int index = 0;
        String msg = "用户导入终止，导入成功：%s,错误信息：%s";
        try {
            for (ExtraUser extraUser : extraUsers) {
                String name = extraUser.getName();

                UserOperateDTO userOperateDto = new UserOperateDTO();
                userOperateDto.setEmail(extraUser.getEmail());
                userOperateDto.setName(name);
                userOperateDto.setCreateTime(Instant.now().toEpochMilli());
                userOperateDto.setPassword(UUIDUtil.newUUID());
                userOperateDto.setId(extraUser.getName());
                userOperateDto.setActive(true);
                userOperateDto.setPhone(extraUser.getPhone());
                userOperateDto.setSource(UserConstants.Source.EXTRA.getValue());
                userOperateDto.setCreateTime(Instant.now().toEpochMilli());
                userOperateDto.setRoleInfoList(roleInfoList);
                Integer n = extUserMapper.selectByPrimaryKey(name);
                if(n > 0){
                    extUserMapper.updateUser(userOperateDto);
                }else {
                    userService.insert(userOperateDto);
                }
                extraUserMapper.deleteByPrimaryKey(extraUser.getId());
                index++;

            }
        } catch (Exception e) {
            termination = true;
            msg = String.format(msg, index, e.getMessage());
        }

        if (termination) {
            F2CException.throwException(msg);
        }
    }
}
