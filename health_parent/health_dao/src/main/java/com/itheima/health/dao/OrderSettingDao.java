package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    /**
     * 所选日期是否可以预约
     *
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 更新已预约人数
     *
     * @param orderSetting
     * @return
     */
    int editReservationsByOrderDate(OrderSetting orderSetting);

    /**
     * 更新可预约数量
     *
     * @param orderSetting
     */
    void updateNumber(OrderSetting orderSetting);

    /**
     * 添加预约设置
     *
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 根据月份查询预约信息
     *
     * @param month
     * @return
     */
    List<Map<String, Integer>> getOrderSettingByMonth(String month);


}
