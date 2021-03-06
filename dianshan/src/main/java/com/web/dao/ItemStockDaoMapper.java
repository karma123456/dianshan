package com.web.dao;

import com.web.dbuitl.ItemStockDao;
import org.apache.ibatis.annotations.Param;

public interface ItemStockDaoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Mon Apr 29 22:34:45 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Mon Apr 29 22:34:45 CST 2019
     */
    int insert(ItemStockDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Mon Apr 29 22:34:45 CST 2019
     */
    int insertSelective(ItemStockDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Mon Apr 29 22:34:45 CST 2019
     */
    ItemStockDao selectByPrimaryKey(Integer id);
    ItemStockDao selectByItemId(Integer Itemid);
    int deleteByItemId(Integer id);
    //param用于指名对应字段decreaseStock
    int decreaseStock(@Param("itemId") Integer itemId,@Param("amount") Integer amount);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Mon Apr 29 22:34:45 CST 2019
     */
    int updateByPrimaryKeySelective(ItemStockDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Mon Apr 29 22:34:45 CST 2019
     */
    int updateByPrimaryKey(ItemStockDao record);
    int updateByStock(ItemStockDao record);
}