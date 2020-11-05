package com.itheima.health.dao;

import com.itheima.health.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    /**
     * 新增
     *
     * @param order
     */
    void add(Order order);

    /**
     * 判断是否重复预约
     *
     * @param order
     * @return
     */
    List<Order> findByCondition(Order order);


    /**
     * 查询预约信息
     *
     * @param id
     * @return
     */
    Map<String, Object> findById4Detail(int id);
    // Map findById4Detail(Integer id);
    // Integer findOrderCountByDate(String date);
    // Integer findOrderCountAfterDate(String date);
    // Integer findVisitsCountByDate(String date);
    // Integer findVisitsCountAfterDate(String date);
    // List<Map> findHotSetmeal();
}
