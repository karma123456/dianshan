package com.web.dao;

import com.web.dbuitl.SequenceDao;

public interface SequenceDaoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Sun May 05 16:22:01 CST 2019
     */
    int deleteByPrimaryKey(String name);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Sun May 05 16:22:01 CST 2019
     */
    int insert(SequenceDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Sun May 05 16:22:01 CST 2019
     */
    int insertSelective(SequenceDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Sun May 05 16:22:01 CST 2019
     */
    SequenceDao selectByPrimaryKey(String name);
    SequenceDao getSequenceByName(String name);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Sun May 05 16:22:01 CST 2019
     */
    int updateByPrimaryKeySelective(SequenceDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Sun May 05 16:22:01 CST 2019
     */
    int updateByPrimaryKey(SequenceDao record);
}