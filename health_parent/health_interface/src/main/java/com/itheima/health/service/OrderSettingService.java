package com.itheima.health.service;

import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    /**
     * 批量导入
     *
     * @param orderSettingList
     */
    void add(List<OrderSetting> orderSettingList);

    /**
     * 根据月份查询预约信息
     *
     * @param month
     * @return
     */
    List<Map<String, Integer>> getOrderSettingByMonth(String month);

    /**
     * 预约设置
     *
     * @param orderSetting
     */
    void editNumberByDate(OrderSetting orderSetting);
}
