package com.fit2cloud.support.dto.response;

import lombok.Data;

/**
 * Created by maguohao on 2019/11/12.
 */
@Data
public class AppInfo {

    private String frontEndUrl;
    private String adminUrl;
    private String username;
    private String password;
    private String authUrl;

    public AppInfo(String authUrl) {
        this.authUrl = authUrl;
    }

    public AppInfo(String frontEndUrl, String adminUrl, String authUrl, String username, String password) {
        this.authUrl = authUrl;
        this.username = username;
        this.password = password;
        this.frontEndUrl = frontEndUrl;
        this.adminUrl = adminUrl;
    }

}
