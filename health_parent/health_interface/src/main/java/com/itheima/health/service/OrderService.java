package com.itheima.health.service;

import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Order;

import java.util.Map;

public interface OrderService {
    /**
     * 提交预约
     *
     * @param paramsMap
     * @return
     */
    Order submit(Map<String, String> paramsMap) throws MyException;

    /**
     * 查询预约信息
     *
     * @param id
     * @return
     */
    Map<String, Object> findById(int id);
}
