package com.web.error;

public enum EmBusinessError implements CommonError {
    //通用错误类型20000
    PARAMETER_VALIDATION_ERROR(20001,"参数不合法"),
    UNKNOWN_ERROE(20001,"未知错误"),
    //用户信息错误10000
    USER_NOT_EXIST(10001,"用户不存在"),
    USERNAME_NOT_EXIST(10002,"用户名或者密码不正确"),
    TELPHONE_NOT_EXIST(10003,"手机号不存在，请注册"),
    USER_NOLOGIN(10004,"用户未登陆"),
    //交易信息错误30000
    STOCK_NOT_ENOUGH(30001,"库存不足"),
    ;
    private EmBusinessError(int errCode,String errMsg){
        this.errCode=errCode;
        this.errMsg=errMsg;
    }
    private int errCode;
    private String errMsg;
    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg=errMsg;
        return this;
    }
}
