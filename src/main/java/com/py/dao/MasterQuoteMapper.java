package com.py.dao;

import com.py.bean.MasterQuote;
import com.py.bean.QuoteSynopsis;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterQuoteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MasterQuote record);

    int insertSelective(MasterQuote record);

    MasterQuote selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MasterQuote record);

    int updateByPrimaryKey(MasterQuote record);

    MasterQuote selectByPrimary(MasterQuote mq);

    List<QuoteSynopsis> selectQuoteByOrderNo(MasterQuote mq);
}