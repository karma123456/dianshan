package com.web.service.impl;

import com.web.dao.UserDaoMapper;
import com.web.dao.UserPasswordDaoMapper;
import com.web.dbuitl.UserDao;
import com.web.dbuitl.UserPasswordDao;
import com.web.error.BusinessException;
import com.web.error.EmBusinessError;
import com.web.service.UserService;
import com.web.service.model.UserModel;
import com.web.validator.ValidationRusult;
import com.web.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validation;
import java.util.List;
import java.util.stream.Collectors;

@Service//这是逻辑层
public class UserServiceImpl implements UserService {
  @Autowired
  private UserDaoMapper userDaoMapper;
    @Autowired
    private UserPasswordDaoMapper userPasswordDaoMapper;

    @Autowired
    private ValidatorImpl validator;
    public UserModel getUserById(Integer id){
        //调用userdaomapper获取到对应的用户的dataobject
        UserDao userDao = userDaoMapper.selectByPrimaryKey(id);
        if(userDao==null){
            return null;
        }
        //通过用户id获取对应的用户加密密码信息
        UserPasswordDao userPasswordDao = userPasswordDaoMapper.selectByUserId(userDao.getId());

        return convertFromDataObject(userDao,userPasswordDao);
    }
//注册接口
    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        ValidationRusult result = validator.vallidate(userModel);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getMsg());
        }
        //实现Userdao转dataobject方法
        UserDao userDao=convertFormModel(userModel);
        try{
            userDaoMapper.insertSelective(userDao);
        }catch(DuplicateKeyException ex){
            if(ex.getMessage().equals("name_unique_index")){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户名已被注册");
            }
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号已被注册");
        }

        userModel.setId(userDao.getId());

        UserPasswordDao userPasswordDao = convertPassFormModel(userModel);
        userPasswordDaoMapper.insertSelective(userPasswordDao);
        return ;
    }
    //用户名登陆
    @Override
    public UserModel validateLogin(String name, String password) throws BusinessException {
        //通过用户名获取用户信息
        UserDao userDao = userDaoMapper.selectByName(name);
        if(userDao == null){
            throw new BusinessException(EmBusinessError.USERNAME_NOT_EXIST);
        }
        UserPasswordDao userPasswordDao = userPasswordDaoMapper.selectByUserId(userDao.getId());
        UserModel userModel = convertFromDataObject(userDao,userPasswordDao);
        //比对用户信息内加密密码是否和传输的一致
        if(!StringUtils.equals(password,userModel.getEncrptPassword())){
            throw new BusinessException(EmBusinessError.USERNAME_NOT_EXIST);
    }
        return userModel;
    }
    //手机号码登陆接口
    @Override
    public UserModel validatebyphone(String telphone) throws BusinessException {
        //通过手机号获取用户信息
        UserDao userDao = userDaoMapper.selectByTelphone(telphone);
        if(userDao == null){
            throw new BusinessException(EmBusinessError.TELPHONE_NOT_EXIST);
        }
        UserPasswordDao userPasswordDao = userPasswordDaoMapper.selectByUserId(userDao.getId());
        UserModel userModel = convertFromDataObject(userDao,userPasswordDao);

        return userModel;
    }
    //用户列表
    @Override
    public List<UserModel> listUser() {
        List<UserDao> userDaoList = userDaoMapper.listUser();
        List<UserModel> userModelList = userDaoList.stream().map(userDao -> {
            UserPasswordDao userPasswordDao = userPasswordDaoMapper.selectByUserId(userDao.getId());
            UserModel userModel = this.convertFromDataObject(userDao,userPasswordDao);
            return userModel;
        }).collect(Collectors.toList());
        return userModelList;
    }
//用户更新
    @Override
    public UserModel update(UserModel userModel) throws BusinessException {

        UserDao userDao = this.convertFormModel(userModel);
        userDaoMapper.updateByPrimaryKeySelective(userDao);
        return this.getUserById(userModel.getId());
    }

    private UserPasswordDao convertPassFormModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserPasswordDao userPasswordDao = new UserPasswordDao();
        userPasswordDao.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDao.setUserId(userModel.getId());

        return userPasswordDao;
    }
    private UserDao convertFormModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserDao userDao=new UserDao();
        BeanUtils.copyProperties(userModel,userDao);
        return userDao;
    }

    private UserModel convertFromDataObject(UserDao userDao, UserPasswordDao userPasswordDao){
        if(userDao==null){
            return null;
        }

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDao,userModel);

        if(userPasswordDao!=null){
            userModel.setEncrptPassword(userPasswordDao.getEncrptPassword());
        }
        return userModel;
    }

}
