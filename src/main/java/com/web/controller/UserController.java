package com.web.controller;

import com.web.controller.viewobject.UserVO;
import com.web.dbuitl.ItemDao;
import com.web.error.BusinessException;
import com.web.error.EmBusinessError;
import com.web.response.CommonReturnType;
import com.web.service.UserService;
import com.web.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller//控制层bean
@RequestMapping("/user")//通过请求user访问
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //用户登录接口
    @RequestMapping(value = "/login",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name="name")String name,
                                  @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
       //入参校验
        if(org.apache.commons.lang3.StringUtils.isEmpty(name)||
                StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //用户登录服务，用来校验用户登陆是否合法
        UserModel userModel = userService.validateLogin(name,this.EncodeMd5(password));

        //将用户凭证加入到用户登录成功的session中
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        return CommonReturnType.creat(userModel);
    }
    //用户手机号登陆接口
    @RequestMapping(value = "/byphone",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType byphone(@RequestParam(name="telphone")String telphone,
                                    @RequestParam(name="otpcode")String otpcode) throws BusinessException {
        //入参校验
        if(org.apache.commons.lang3.StringUtils.isEmpty(telphone)||
                StringUtils.isEmpty(otpcode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //用户登录服务，用来校验用户登陆是否合法
        UserModel userModel = userService.validatebyphone(telphone);
        //将用户凭证加入到用户登录成功的session中
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        //this.httpServletRequest.getSession().setAttribute("user",userModel.getName());
        return CommonReturnType.creat(null);
    }
    //用户注册接口
    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "telphone" )String telphone,
                                     @RequestParam(name = "name")String name,
                                     @RequestParam(name = "otpcode")String otpCode,
                                     @RequestParam(name = "gender")Integer gender,
                                     @RequestParam(name = "age")Integer age,
                                     @RequestParam(name = "password")String password,
                                     @RequestParam(name = "img")String img) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号和对应的otpcode相符合
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
        if(!com.alibaba.druid.util.StringUtils.equals(otpCode,inSessionOtpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证失败");
        }
        //用户注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setAge(age);
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword(this.EncodeMd5(password));
        userModel.setThridPartyId(img);
        userService.register(userModel);
        return CommonReturnType.creat(null);
    }

    public String EncodeMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        String newstr = base64en.encode(md5.digest(str.getBytes("UTF-8")));
        return newstr;
    }
    //获取otp短信接口
    @RequestMapping(value = "/getotp",method  = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telphone") String telphone){
        //需要按照一定的规则生成OTP验证码
        Random random=new Random();
        int randomInt=random.nextInt(99999);
        randomInt+=10000;
        String otpCode=String.valueOf(randomInt);

        //将OTP验证码同对应手机号码关联,使用httpsession方式
        httpServletRequest.getSession().setAttribute(telphone,otpCode);
        //将otp验证码通过短信发送给用户
        System.out.println("tel:"+telphone+"& otpCode:"+otpCode);
        return CommonReturnType.creat(otpCode);
    }

    @RequestMapping("/get")//通过请求get访问
    @ResponseBody//该方法返回结果直接写入HTTP响应正文
    //通过ID参数获取对应用户信息
    public CommonReturnType getUser(@RequestParam(name="id") Integer id)throws BusinessException{
        UserModel userModel = userService.getUserById(id);
        //如果获取的对应用户信息不存在
        if(userModel==null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);//抛出异常
        }
        UserVO userVO=convertFromModel(userModel);
        return CommonReturnType.creat(userVO);
    }
    //获取用户列表
    @RequestMapping(value = "/list",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listUser(){
        List<UserModel> userModelList = userService.listUser();
        List<UserVO> userVOList = userModelList.stream().map(userModel -> {
            UserVO userVO = this.convertFromModel(userModel);
            return userVO;
        }).collect(Collectors.toList());
        return CommonReturnType.creat(userVOList);
    }
    //用户更新
    @RequestMapping(value = "update",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType updateUser(@RequestParam(name = "id")Integer id,
                                       @RequestParam(name = "telphone" )String telphone,
                                       @RequestParam(name = "name")String name,
                                       @RequestParam(name = "gender")Integer gender,
                                       @RequestParam(name = "age")Integer age,
                                       @RequestParam(name = "img")String img) throws BusinessException {
        UserModel userModel = new UserModel();
        userModel.setId(id);
        userModel.setName(name);
        userModel.setTelphone(telphone);
        userModel.setAge(age);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setThridPartyId(img);

        UserModel userModel1 = userService.update(userModel);
        UserVO userVO = this.convertFromModel(userModel1);
        return CommonReturnType.creat(userVO);
    }
    /***
     * 判断是否登陆
     */
    @RequestMapping(value = "/islogin")
    @ResponseBody
    public CommonReturnType islogin() throws BusinessException {
        //获取用户登陆信息
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOLOGIN,"用户还没有登陆");
        }
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        return CommonReturnType.creat(userModel);
    }

    //用UserModel对象封装成UserVO对象
    public UserVO convertFromModel(UserModel userModel){
        if(userModel==null){
            return null;
        }
        UserVO userVO=new UserVO();
        BeanUtils.copyProperties(userModel,userVO);//将UserModel中的属性复制到UserVo中，对应属性的类型需一致
        return userVO;
    }
}
