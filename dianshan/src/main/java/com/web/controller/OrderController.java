package com.web.controller;

import com.web.dbuitl.OrderDao;
import com.web.error.BusinessException;
import com.web.error.EmBusinessError;
import com.web.response.CommonReturnType;
import com.web.service.OrderService;
import com.web.service.model.OrderModel;
import com.web.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/order")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    //封装下单
    @RequestMapping(value = "/createOrder",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name = "itemId")Integer itemId,
                                        @RequestParam(name = "amount")Integer amount,
                                        @RequestParam(name = "promoId",required = false)Integer promoId)throws BusinessException {
       //获取用户登陆信息
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOLOGIN,"用户还没有登陆");
        }
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        OrderModel orderModel = orderService.createOrder(userModel.getId(),itemId,promoId,amount);
        return CommonReturnType.creat(null);
    }
    /***
     * 删除操作
     * 通过id删除
     */
    @RequestMapping(value = "/detele",method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType deleteOrder(@RequestParam(name = "id")String id){
        orderService.delete(id);
        return CommonReturnType.creat(null);
    }
    /***
     * 查看
     * list
     */
    @RequestMapping(value = "/list",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType orderList(){
        List<OrderDao> orderDao = orderService.list();
        return CommonReturnType.creat(orderDao);
    }
}
