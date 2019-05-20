package com.py.dao;

import com.py.bean.Order;
import com.py.bean.OrderSynopsis;
import com.py.bean.OrderSynopsisMaster;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByPrimary(Order order);

    List<OrderSynopsisMaster> selectOrderByStatusAndMajorClassId(Integer[] majorClassId);

    int updateOrderState(Order order);

    List<OrderSynopsis> selectSynopsisByStatus(@Param("state")String state, @Param("userId")String userId);
}