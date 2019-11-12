package com.fit2cloud.support.dto.response;


import com.fit2cloud.support.common.constants.AliYunMarketConstants;
import com.fit2cloud.support.model.Product;
import lombok.Data;

/**
 * Created by maguohao on 2019/11/12.
 */
@Data
public class CreateInstanceResponse {

    private String instanceId;

    private AppInfo appInfo;

    private Info info;

    public CreateInstanceResponse(String instanceId, AppInfo appInfo) {
        this.instanceId = instanceId;
        this.appInfo = appInfo;
    }

    public CreateInstanceResponse(String instanceId, AppInfo appInfo, Info info) {
        this.instanceId = instanceId;
        this.appInfo = appInfo;
        this.info = info;
    }

    public static CreateInstanceResponse getSuccess(String instanceId, String userName, String password, Product product) {

        AppInfo info = new AppInfo(product.getFrontEndUrl(), product.getAdminUrl(), product.getAuthUrl(), userName, password);

        return new CreateInstanceResponse(instanceId, info);
    }

    public static CreateInstanceResponse getFail() {

        return new CreateInstanceResponse(AliYunMarketConstants.failCode, null);
    }

    public static CreateInstanceResponse getFail(Info info) {

        return new CreateInstanceResponse(AliYunMarketConstants.failCode, null,info);
    }
}
