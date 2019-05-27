package com.web.service.impl;

import com.web.dao.PromoDaoMapper;
import com.web.dbuitl.PromoDao;
import com.web.service.PromoService;
import com.web.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PromoServiceImpl implements PromoService {
    @Autowired
    PromoDaoMapper promoDaoMapper ;
    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        //获取对应的商品秒杀id
        PromoDao promoDao = promoDaoMapper.selectByItemId(itemId);
        //dataobject->model
        PromoModel promoModel = convertFromDataObject(promoDao);
        if(promoModel == null){
            return null;
        }
        //判断当前时间是否秒杀活动即将开始或正在进行
        DateTime now = new DateTime();
        if(promoModel.getStartDate().isAfterNow()){
            promoModel.setStatus(1);
        }else if(promoModel.getEndDate().isBeforeNow()){
            promoModel.setStatus(3);
        }else{
            promoModel.setStatus(2);
        }

        return promoModel;
    }

    private PromoModel convertFromDataObject(PromoDao promoDao){
        if(promoDao == null){
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDao,promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promoDao.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promoDao.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDao.getEndDate()));
        return promoModel;
    }
}
