package com.web.service;

import com.web.dbuitl.ItemDao;
import com.web.error.BusinessException;
import com.web.service.model.ItemModel;

import java.util.List;

/***
 * 商品逻辑层
 * */
public interface ItemService {
    //创建商品
    ItemModel creatItem(ItemModel itemModel) throws BusinessException;
    //商品列表浏览
    List<ItemModel> listItem();
    //商品详情浏览
    ItemModel getItemById(Integer id);
    //删除商品
    void delete(Integer id);
    //更新商品
    ItemModel update(ItemModel itemModel) throws BusinessException;
    //库存扣减(下单模块)
    boolean decreaseStock(Integer itemId,Integer amount) throws BusinessException;
    //商品销量增加
    void increaseSales(Integer itemId,Integer amount) throws BusinessException;
}
