package com.transpeed.spdb.util;

import lombok.Data;

@Data
public class HttpResult {

    public HttpResult() {
    }

    public HttpResult(Integer status, String data) {
        this.status = status;
        this.data = data;
    }

    /**
     * 状态码
     */
    private Integer status;
    /**
     * 返回数据
     */
    private String data;
}
