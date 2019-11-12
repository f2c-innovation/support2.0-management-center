package com.fit2cloud.support.service;

import com.fit2cloud.support.common.GlobalConfigurations;
import com.fit2cloud.support.common.constants.AliYunMarketConstants;
import com.fit2cloud.support.common.constants.MessageConst;
import com.fit2cloud.support.common.constants.ThirdPartyConstants;
import com.fit2cloud.support.common.utils.*;
import com.fit2cloud.support.dto.FreeLanding;
import com.fit2cloud.support.dto.response.CreateInstanceResponse;
import com.fit2cloud.support.dto.response.Info;
import com.fit2cloud.support.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AliyunMarketRecordService {

//    @Resource
//    private AliyunMarketRecordMapper aliyunMarketRecordMapper;
//    @Resource
//    private AgentSubscriptionService agentSubscriptionService;
//    @Resource
//    private CompanyMapper companyMapper;
//    @Resource
//    private CompanyService companyService;
//    @Resource
//    private UserMapper userMapper;
//    @Resource
//    private UserRoleMapper userRoleMapper;
//    @Resource
//    private ProductService productService;
//    @Resource
//    private SubscriptionService subscriptionService;
//    @Resource
//    private AgentService agentService;
//
//    /**
//     * 新订阅产品，
//     * 创建默认公司
//     * 创建默认用户
//     * 插入权限
//     */
//    public Object createInstance(HttpServletRequest request, AliyunMarketRecord aliyunMarketRecord) throws Exception {
//        try {
//            //校验token,本地调试不验证
//            if (GlobalConfigurations.isReleaseMode()) {
//                if (!AliYunMarketUtil.validateToken(request)) {
//                    return AliYunMarketUtil.generateErrorResponse("token is invalid");
//                }
//            }
//            //先查看阿里云公司是否存在 没有则创建
//            //默认所有从此接口进来的用户都是暂时设置为阿里云公司的员工
//            Company aliyunCompany = createAliyunCompany();
//            Agent aliyunAgent = createAliyunAgent();
//
//            //	请不要阻塞此接口，若耗时较长，可使用队列做缓冲，设置instanceId=0，然后立即返回。
//            //  若操作失败也请设置instanceId=0，云市场都会再次调用，直到获取到instanceId。
//            log.info("aliyun 传入信息：{}", ReflectionToStringBuilder.toString(aliyunMarketRecord));
//            String instanceId = UUIDUtil.newUUID();
//            aliyunMarketRecord.setInstanceId(instanceId);
//            aliyunMarketRecord.setCreateTime(System.currentTimeMillis());
//            aliyunMarketRecordMapper.insert(aliyunMarketRecord);
//
//            User user = null;
//            //查询所有用户，包括被删除的
//            if (StringUtils.isNotBlank(aliyunMarketRecord.getEmail())) {
//                user = getUserByEmail(aliyunMarketRecord, true);
//            }
//            if (user == null) {
//                //生成用户
//                if (StringUtils.isNotBlank(aliyunMarketRecord.getEmail())) {
//                    User newUser = new User();
//                    String userId = UUIDUtil.newUUID();
//                    newUser.setId(userId);
//                    newUser.setUid(aliyunMarketRecord.getAliUid());
//                    newUser.setToken(EncryptUtils.md5Encrypt(userId).toString());
//                    newUser.setEmail(aliyunMarketRecord.getEmail());
//                    newUser.setCompanyId(aliyunCompany.getId());
//                    newUser.setName(MessageConst.completion);
//                    newUser.setContent(MessageConst.completion);
//                    newUser.setIsDelete(false);
//                    newUser.setPhone(aliyunMarketRecord.getMobile());
//                    newUser.setCreateTime(System.currentTimeMillis());
//                    newUser.setType(ThirdPartyConstants.Type.ali.name());
//                    userMapper.insert(newUser);
//                    user = newUser;
//                    //用户权限
//                    UserRole userRole = new UserRole();
//                    userRole.setId(UUIDUtil.newUUID());
//                    userRole.setUserId(userId);
//                    userRole.setRoleId(AliYunMarketConstants.role_id);
//                    userRoleMapper.insert(userRole);
//                } else {
//                    //不创建新用户
//                }
//            } else {
//                //对于已经存在的用户，判断是否是老用户，是老用户的话，把老用户的uid给补上
//                if (StringUtils.isEmpty(user.getUid())) {
//                    user.setUid(aliyunMarketRecord.getAliUid());
//                    userMapper.updateByPrimaryKeySelective(user);
//                }
//            }
//
//            if (user != null) {
//                if (user.getIsDelete()) {
//                    user.setIsDelete(false);
//                }
//            }
//
//            //获取用户当前的公司
//            String companyId = null;
//
//            if (user != null) {
//                companyId = user.getCompanyId();
//            } else {
//                companyId = aliyunCompany.getId();
//            }
//
//            Company company = this.companyService.getCompanyById(companyId);
//            //把最新的instanceId绑定到公司
//            company.setInstanceId(instanceId);
//            this.companyMapper.updateByPrimaryKey(company);
//            //产品表的sku_id
//            Product product = this.productService.selectProductByRelationSkuId(aliyunMarketRecord.getSkuId());
//            if (product == null) {
//                throw new Exception("管理员未给产品设置SkuId,请尽快设置SkuId,传入sku_id：" + aliyunMarketRecord.getSkuId());
//            }
//
//            //添加订阅功能
//            SubscriptionDto subscriptionDto = new SubscriptionDto();
//            subscriptionDto.setInstanceId(instanceId);
//            subscriptionDto.setProductId(product.getId());
//            subscriptionDto.setOrderId(aliyunMarketRecord.getOrderId());
//            subscriptionDto.setOrderBizId(aliyunMarketRecord.getOrderBizId());
//            subscriptionDto.setUid(aliyunMarketRecord.getAliUid());
//            subscriptionDto.setCreateTime(System.currentTimeMillis());
//            subscriptionDto.setExpirationTime(DateUtil.getMillis(aliyunMarketRecord.getExpiredOn(), DateUtil.yyyyMMdd_HHmmss));
//            subscriptionDto.setSaleId("5861c990-0890-46a5-84ae-1e1212f16488");//销售id
//            subscriptionDto.setCompanyId(company.getId());
//            subscriptionDto.setEmail(aliyunMarketRecord.getEmail());
//            subscriptionDto.setAuthorizationCount(product.getAuthorizationCount());
//            subscriptionService.insertSubscription(subscriptionDto);
//            //添加订阅代理关系
//            this.agentSubscriptionService.insert(subscriptionDto.getId(), aliyunAgent.getId());
//            return CreateInstanceResponse.getSuccess(instanceId, aliyunMarketRecord.getEmail(), user != null ? user.getId() : null, product);
//        } catch (Exception e) {
//            log.error("购买阿里云错误：{}", e);
//            return CreateInstanceResponse.getFail(new Info(false, e.getMessage()));
//        }
//    }
//
//    public Object renewInstance(HttpServletRequest request, AliyunMarketRecord aliyunMarketRecord) {
//
//        log.info("阿里云续费：{}", ReflectionToStringBuilder.toString(aliyunMarketRecord));
//
//        try {
//            //校验token，本地调试不验证
//            if (GlobalConfigurations.isReleaseMode()) {
//                if (!AliYunMarketUtil.validateToken(request)) {
//                    return AliYunMarketUtil.generateErrorResponse("token is invalid");
//                }
//            }
//            AliyunMarketRecord select = aliyunMarketRecordMapper.selectByPrimaryKey(aliyunMarketRecord.getInstanceId());
//            select.setExpiredOn(aliyunMarketRecord.getExpiredOn());
//            aliyunMarketRecordMapper.updateByPrimaryKey(select);
//            //修改订阅有效期
//            Subscription subscription = this.subscriptionService.getSubscriptionByInstanceId(aliyunMarketRecord.getInstanceId());
//            subscription.setExpirationTime(DateUtil.getMillis(aliyunMarketRecord.getExpiredOn(), DateUtil.yyyyMMdd_HHmmss));
//            this.subscriptionService.updateSubscription(subscription);
//            //如果续费用户被系统管理员删除，重新启动
//            CompanyExample companyExample = new CompanyExample();
//            CompanyExample.Criteria criteria = companyExample.createCriteria();
//            criteria.andInstanceIdEqualTo(aliyunMarketRecord.getInstanceId());
//            List<Company> companies = companyMapper.selectByExample(companyExample);
//            companies.forEach(company -> {
//                if (company.getIsDelete()) {
//                    company.setIsDelete(false);
//                    companyMapper.updateByPrimaryKeySelective(company);
//                    UserExample userExample = new UserExample();
//                    userExample.createCriteria().andCompanyIdEqualTo(company.getId());
//                    List<User> users = userMapper.selectByExample(userExample);
//                    users.forEach(user -> {
//                        user.setIsDelete(false);
//                        userMapper.updateByPrimaryKeySelective(user);
//                    });
//                }
//            });
//            return AliYunMarketUtil.generateCorrectResponse();
//        } catch (Exception e) {
//            log.error("续费错误日志：{}", e);
//            return AliYunMarketUtil.generateErrorResponse(e.getMessage());
//        }
//    }
//
//    /**
//     * 阿里云过来的订阅都默认归属于阿里云公司
//     * 默认只有一个阿里云公司
//     */
//    public Company createAliyunCompany() {
//        String companyName = "阿里云";
//        String companyId = AliYunMarketUtil.getDefaultAliyunCompanyId();
//        Company company = this.companyService.getCompanyById(companyId);
//        if (company == null) {
//            company = new Company();
//            company.setId(companyId);
//            company.setEmail(MessageConst.completion);
//            company.setIsDelete(false);
//            company.setIsOwn(false);
//            company.setPhone(MessageConst.completion);
//            company.setName(companyName);
//            company.setAddress(MessageConst.completion);
//            company.setContent(MessageConst.completion);
//            company.setCreateTime(System.currentTimeMillis());
//            company.setType(ThirdPartyConstants.Type.ali.name());
//            companyMapper.insert(company);
//        } else {
//            if (company.getIsDelete().equals(true)) {
//                company.setIsDelete(false);
//                this.companyMapper.updateByPrimaryKey(company);
//            }
//        }
//        return company;
//    }
//
//    /**
//     * 阿里云代理
//     *
//     * @return
//     */
//    public Agent createAliyunAgent() {
//        String agentName = "阿里云代理";
//        String agentId = AliYunMarketUtil.getDefaultAliyunAgentId();
//        Agent agent = this.agentService.getByAgentId(agentId);
//        if (agent == null) {
//            agent = new Agent();
//            agent.setName(agentName);
//            agent.setId(agentId);
//            agent.setIsDelete(false);
//            agent.setCreateTime(System.currentTimeMillis());
//            agent.setDescription("这是自动生成的阿里云代理");
//            this.agentService.insert(agent);
//        } else {
//            if (agent.getIsDelete().equals(true)) {
//                agent.setIsDelete(false);
//                this.agentService.update(agent);
//            }
//        }
//        return agent;
//    }
//
//    public String noLogin(FreeLanding freeLanding) {
//        //查看是否过期
//        AliyunMarketRecord aliyunMarketRecord = aliyunMarketRecordMapper.selectByPrimaryKey(freeLanding.getInstanceId());
//        //查看是否过期
//        /*boolean isOverdue = DateTime.parse(AliyunMarketRecord.getExpiredOn(), DateTimeFormat.forPattern(DateUtil.yyyyMMdd_HHmmss)).isBeforeNow();
//        if (isOverdue) {
//            return "login";
//        }*/
//        //用户是否存在
//
//        User user = getUserByEmail(aliyunMarketRecord, false);
//        if (user != null) {
//            UsernamePasswordToken token = new UsernamePasswordToken(StringUtils.trim(user.getEmail()), AliYunMarketConstants.AliYunFlag);
//            Subject subject = SecurityUtils.getSubject();
//            subject.login(token);
//            if (subject.isAuthenticated()) {
//                return "redirect:/";
//            }
//        }
//        return "login";
//    }
//
//    /**
//     * true为查询所有的用户（包括被删除的用户），false只能查询没被删除的
//     *
//     * @param aliyunMarketRecord
//     * @param isCreate
//     * @return
//     */
//    private User getUserByEmail(AliyunMarketRecord aliyunMarketRecord, boolean isCreate) {
//        if (aliyunMarketRecord == null) {
//            return null;
//        }
//        UserExample userExample = new UserExample();
//        UserExample.Criteria criteria = userExample.createCriteria();
//        criteria.andEmailEqualTo(aliyunMarketRecord.getEmail());
//        if (!isCreate) {
//            criteria.andIsDeleteEqualTo(false);
//        }
//        List<User> users = userMapper.selectByExample(userExample);
//        if (CollectionUtils.isNotEmpty(users)) {
//            return users.get(0);
//        } else {
//            return null;
//        }
//    }
}
