package com.web.service;

import com.web.error.BusinessException;
import com.web.service.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
    UserModel validateLogin(String name,String password) throws BusinessException;
    UserModel validatebyphone(String telphone) throws BusinessException;
    //用户列表
    List<UserModel> listUser();
    //用户更新
    UserModel update(UserModel userModel) throws BusinessException;
}
