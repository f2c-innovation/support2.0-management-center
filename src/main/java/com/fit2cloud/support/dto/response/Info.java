package com.fit2cloud.support.dto.response;

import lombok.Data;

@Data
public class Info {

    // 请求是否成功
    private boolean success = false;
    // 描述信息
    private Object message;

    public Info(boolean success, Object msg) {
        this.success = success;
        this.message = msg;
    }
}
