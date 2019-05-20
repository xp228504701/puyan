package com.py.service;

import com.py.bean.*;
import com.py.dao.MasterQuoteMapper;
import com.py.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MasterQuoteMapper masterQuoteMapper;




    /**
     * 根据订单状态获取订单的简单信息
     * @param state
     * @param userId
     * @return
     */
    public List<OrderSynopsis> selectSynopsisByStatus(String state, String userId) {

       return  orderMapper.selectSynopsisByStatus(state,userId);
    }

    public void insertOrder(Order o) {
        orderMapper.insertSelective(o);
    }

    public Order selectByPrimaryKey(Integer orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    public Order selectByPrimary(String orderNo) {
        Order o = new Order();
        o.setOrderNo(orderNo);
        return orderMapper.selectByPrimary(o);

    }

    public int updateOrderState(Order order) {
        return orderMapper.updateOrderState(order);
    }


    /**
     * 根据大类id查找订单
     * @param majorClassId
     */
    public List<OrderSynopsisMaster> selectOrderByStatusAndMajorClassForMaster(Integer[] majorClassId) {

        return orderMapper.selectOrderByStatusAndMajorClassId(majorClassId);
    }

    /**
     * 查找已接单信息
     * @param mq
     * @return
     */
    public MasterQuote selectOrderQuoteByPrimary(MasterQuote mq) {
        return masterQuoteMapper.selectByPrimary(mq);
    }

    /**
     * 查找已报价的信息
     * @param mq
     */
    public List<QuoteSynopsis> selectQuoteByOrderNo(MasterQuote mq) {
        return masterQuoteMapper.selectQuoteByOrderNo(mq);
    }

    /**
     * 接受报价
     * @param orderNo
     * @param masterQuoteId
     */
    public String acceptQuote(String orderNo, String masterQuoteId) {

        MasterQuote masterQuote = masterQuoteMapper.selectByPrimaryKey(Integer.parseInt(masterQuoteId));
        if(masterQuote != null){
            masterQuote.setStatus(2);
            masterQuoteMapper.updateByPrimaryKeySelective(masterQuote);

            Order order = new Order();
            order.setOrderNo(orderNo);
            Order o = orderMapper.selectByPrimary(order);

            o.setState(1);
            o.setMasterId(String.valueOf(masterQuote.getMasterId()));
            o.setFee(masterQuote.getMoney());
            orderMapper.updateByPrimaryKey(o);
            return o.getOrderNo();
        }
        return "error: 1001";

    }
}
