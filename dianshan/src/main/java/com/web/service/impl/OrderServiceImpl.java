package com.web.service.impl;

import com.web.dao.OrderDaoMapper;
import com.web.dao.SequenceDaoMapper;
import com.web.dbuitl.OrderDao;
import com.web.dbuitl.SequenceDao;
import com.web.error.BusinessException;
import com.web.error.EmBusinessError;
import com.web.service.ItemService;
import com.web.service.OrderService;
import com.web.service.UserService;
import com.web.service.model.ItemModel;
import com.web.service.model.OrderModel;
import com.web.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SequenceDaoMapper sequenceDaoMapper;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDaoMapper orderDaoMapper;
    @Override
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException {
        //校验下单状态，下单的商品是否存在，用户是否合法，购买数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品信息不存在");
        }

        UserModel userModel = userService.getUserById(userId);
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息不存在");
        }

        if(amount <= 0 || amount >99){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品数量不正确");
        }
        //校验活动信息
        //若等于null则为普通商品信息，非活动商品
        if(promoId != null){
            //(1.校验对应活动是否存在这个适用的秒杀商品)
            if(promoId.intValue() != itemModel.getPromoModel().getId()){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息不正确");
            }else if(itemModel.getPromoModel().getStatus().intValue() != 2){//2.校验活动是否正在进行中
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息还未开始");
            }
        }
        //落单减库存
        boolean result = itemService.decreaseStock(itemId,amount);
        if(!result){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }
        //订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        if(promoId != null){//如果不为空，就取秒杀价格
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else{//否则，取平时价格
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));
        orderModel.setPromoId(promoId);

        //生成订单号
        orderModel.setId(generateOrderNo());
        OrderDao orderDao = convertFromOrderModel(orderModel);
        orderDaoMapper.insertSelective(orderDao);

        //商品销量加
        itemService.increaseSales(itemId,amount);
        //返回前端
        return orderModel;
    }

    @Override
    public void delete(String id) {
        OrderDao orderDao = orderDaoMapper.selectByPrimaryKey(id);
        orderDaoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<OrderDao> list() {
        List<OrderDao> orderDaos = orderDaoMapper.list();
        List<OrderDao> orderDaoList = orderDaos.stream().map(orderDao -> {
            OrderDao orderDao1 = orderDaoMapper.selectByPrimaryKey(orderDao.getId());
            return orderDao1;
        }).collect(Collectors.toList());
        return orderDaoList;
    }

    //生成订单号
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    String generateOrderNo(){
        //订单号有16位
        StringBuilder stringBuilder = new StringBuilder();
        //前8位为时间信息，年月日
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);
        //中间6位为自增序列
        //获取当前sequence
        int sequence = 0;
        SequenceDao sequenceDao = sequenceDaoMapper.getSequenceByName("order_info");

        sequence = sequenceDao.getCurrentValue();
        sequenceDao.setCurrentValue(sequenceDao.getCurrentValue()+sequenceDao.getStep());
        sequenceDaoMapper.updateByPrimaryKeySelective(sequenceDao);

        String sequenceStr = String.valueOf(sequence);
        for(int i = 0;i<6-sequenceStr.length();i++){
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);
        //最后2位为分库分表位
        stringBuilder.append("00");
        return stringBuilder.toString();
    }
    //ordermodel转orderdao
    private OrderDao convertFromOrderModel(OrderModel orderModel){
        if(orderModel == null){
            return null;
        }
        OrderDao orderDao = new OrderDao();
        BeanUtils.copyProperties(orderModel,orderDao);

        orderDao.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        orderDao.setItemPrice(orderModel.getItemPrice().doubleValue());
        return orderDao;
    }
}
