package com.transpeed.spdb.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class CodeMsg {

    public static final CodeMsg SUCESS = new CodeMsg( 0, "成功" );

    public static final CodeMsg SERVERERROR = new CodeMsg( 1001, "失败" );

    public static final CodeMsg SIGNERROR = new CodeMsg( 1000, "签名错误" );

    public static final CodeMsg PARKIDERROR = new CodeMsg( 1000, "停车场编号错误" );

    private Integer code;
    private String msg;

    private CodeMsg() {
    }

    private CodeMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format( this.msg, args );
        return new CodeMsg( code, message );
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }

}
