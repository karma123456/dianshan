package com.web.controller;

import com.web.error.BusinessException;
import com.web.error.EmBusinessError;
import com.web.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    public static final String CONTENT_TYPE_FORMED="application/x-www-form-urlencoded";

    //定义exceptionhandler解决未被controller层吸收的异常
    @ExceptionHandler(Exception.class)//异常处理
    @ResponseStatus(HttpStatus.OK)//自定义异常，同时显示在页面上
    @ResponseBody
    public Object handlerEXception(HttpServletRequest request, Exception ex){

        Map<String,Object> responseData=new HashMap<>();
        if(ex instanceof BusinessException){
            BusinessException businessException=(BusinessException)ex;//将ex强转型成BusinessException
            responseData.put("errCode",businessException.getErrCode());
            responseData.put("errMsg",businessException.getErrMsg());
        }else{
            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROE.getErrCode());
            responseData.put("errMsg",EmBusinessError.UNKNOWN_ERROE.getErrMsg());
        }

        return CommonReturnType.creat(responseData,"fail");
    }
}
