package com.web.dao;

import com.web.dbuitl.UserPasswordDao;

public interface UserPasswordDaoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Apr 14 23:37:38 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Apr 14 23:37:38 CST 2019
     */
    int insert(UserPasswordDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Apr 14 23:37:38 CST 2019
     */
    int insertSelective(UserPasswordDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Apr 14 23:37:38 CST 2019
     */
    UserPasswordDao selectByPrimaryKey(Integer id);
    UserPasswordDao selectByUserId(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Apr 14 23:37:38 CST 2019
     */
    int updateByPrimaryKeySelective(UserPasswordDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Apr 14 23:37:38 CST 2019
     */
    int updateByPrimaryKey(UserPasswordDao record);
}