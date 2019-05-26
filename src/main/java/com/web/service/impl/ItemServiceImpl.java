package com.web.service.impl;

import com.web.dao.ItemDaoMapper;
import com.web.dao.ItemStockDaoMapper;
import com.web.dbuitl.ItemDao;
import com.web.dbuitl.ItemStockDao;
import com.web.error.BusinessException;
import com.web.error.EmBusinessError;
import com.web.service.ItemService;
import com.web.service.PromoService;
import com.web.service.model.ItemModel;
import com.web.service.model.PromoModel;
import com.web.validator.ValidationRusult;
import com.web.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDaoMapper itemDaoMapper;

    @Autowired
    private ItemStockDaoMapper itemStockDaoMapper;

    @Autowired
    private PromoService promoService;
   //将model->dao（数据库写入功能准备）
    private ItemDao convertItemDAOFromItemModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemDao itemDao = new ItemDao();
        BeanUtils.copyProperties(itemModel,itemDao);
        itemDao.setPrice(itemModel.getPrice().doubleValue());
        return itemDao;
    }
    //根据model设置stock表中的库存（数据库写入功能准备）
    private ItemStockDao convertItemStockDAOFromItemModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemStockDao itemStockDao = new ItemStockDao();
        itemStockDao.setItemId(itemModel.getId());
        itemStockDao.setStock(itemModel.getStock());
        return itemStockDao;
    }
    //创建商品
    @Override
    @Transactional
    public ItemModel creatItem(ItemModel itemModel) throws BusinessException {

        //校验入参
        ValidationRusult result = validator.vallidate(itemModel);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getMsg());
        }
        //转入itemmodel—>dataobject
        ItemDao itemDao = this.convertItemDAOFromItemModel(itemModel);
        //写入数据库
        itemDaoMapper.insertSelective(itemDao);
        itemModel.setId(itemDao.getId());

        ItemStockDao itemStockDao = this.convertItemStockDAOFromItemModel(itemModel);
        itemStockDaoMapper.insertSelective(itemStockDao);
        //返回创建完成的对象

        return this.getItemById(itemModel.getId());
    }
//商品列表项
    @Override
    public List<ItemModel> listItem() {
        List<ItemDao> itemDAOList = itemDaoMapper.listItem();
       List<ItemModel> itemModelList = itemDAOList.stream().map(itemDao -> {
          ItemStockDao itemStockDao = itemStockDaoMapper.selectByItemId(itemDao.getId());
          ItemModel itemModel = this.convertModelFromDataObject(itemDao,itemStockDao);
          return itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDao itemDao = itemDaoMapper.selectByPrimaryKey(id);
        if(itemDao == null){
            return null;
        }
        //操作库存数量
        ItemStockDao itemStockDao = itemStockDaoMapper.selectByItemId(itemDao.getId());

        //将dataobject->model
        ItemModel itemModel = convertModelFromDataObject(itemDao,itemStockDao);

        //获取活动商品信息(秒杀用)
        PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
        if(promoModel != null && promoModel.getStatus().intValue() != 3){
            itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }
    //删除商品
    @Override
    public void delete(Integer id) {
        ItemDao itemDao = itemDaoMapper.selectByPrimaryKey(id);
        itemDaoMapper.deleteByPrimaryKey(id);
        itemStockDaoMapper.deleteByItemId(itemDao.getId());
    }
//更新商品
    @Override
    public ItemModel update(ItemModel itemModel) throws BusinessException {
        ValidationRusult result = validator.vallidate(itemModel);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getMsg());
        }
        ItemDao itemDao = this.convertItemDAOFromItemModel(itemModel);
        itemDaoMapper.updateByPrimaryKey(itemDao);
        ItemStockDao itemStockDao = this.convertItemStockDAOFromItemModel(itemModel);
        itemStockDaoMapper.updateByStock(itemStockDao);

        return this.getItemById(itemModel.getId());
    }

    @Override
    @Transactional//保持事物一致性
    public boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException {
        //affectedRow sql语句执行返回受影响的条数
        int affectedRow = itemStockDaoMapper.decreaseStock(itemId,amount);
        if(affectedRow > 0) {
            //更新库存成功
            return true;
        }else{
            return false;
        }
    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) throws BusinessException {
        itemDaoMapper.increaseSales(itemId,amount);
    }

    private ItemModel convertModelFromDataObject(ItemDao itemDao,ItemStockDao itemStockDao){

        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDao,itemModel);
        itemModel.setPrice(new BigDecimal(itemDao.getPrice()));
        itemModel.setStock(itemStockDao.getStock());

        return itemModel;
    }
}
