package com.web.service;

import com.web.service.model.PromoModel;

/***
 *秒杀service
 */
public interface PromoService {
    //根据itemid获取即将进行或正在进行的秒杀活动
    PromoModel getPromoByItemId(Integer itemId);
}
