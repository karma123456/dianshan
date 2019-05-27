package com.web.service;

import com.web.dbuitl.OrderDao;
import com.web.error.BusinessException;
import com.web.service.model.OrderModel;

import java.util.List;

/***
 *订单service
 */

public interface OrderService {
    //用户id，商品id，数量
    //（使用）方法1.通过url上传的秒杀活动id，然后下单接口内校验对应的id是否属于对应的商品且活动已开始
    //方法2：直接在下单接口内判断对应的商品是否存在秒杀活动，若存在进行中则以秒杀价格下单
    OrderModel createOrder(Integer userId,Integer itemId,Integer promoId,Integer amount) throws BusinessException;
    //删除
    void delete(String id);
    //列表
    List<OrderDao> list();
}
