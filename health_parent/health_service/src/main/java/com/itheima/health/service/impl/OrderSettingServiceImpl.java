package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author xlm <tobexlm@163.com>
 * @Date 2020/10/31 0:21
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 批量导入
     *
     * @param orderSettingList
     */
    @Override
    @Transactional
    public void add(List<OrderSetting> orderSettingList) {
        // 遍历
        for (OrderSetting orderSetting : orderSettingList) {
            // 判断是否存在, 通过日期来查询, 注意：日期里是否有时分秒，数据库里的日期是没有时分秒的
            OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
            if (null != osInDB) {
                // 数据库存在这个预约设置, 已预约数量不能大于可预约数量
                if (osInDB.getReservations() > orderSetting.getNumber()) {
                    throw new MyException(orderSetting.getOrderDate() + " 中已预约数量不能大于可预约数量");
                }
                orderSettingDao.updateNumber(orderSetting);
            } else {
                // 不存在
                orderSettingDao.add(orderSetting);
            }
        }
    }

    /**
     * 根据月份查询预约信息
     *
     * @param month
     * @return
     */
    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        month += "%";
        return orderSettingDao.getOrderSettingByMonth(month);
    }

    /**
     * 预约设置
     *
     * @param orderSetting
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //通过日期判断预约设置是否存在？
        OrderSetting os = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        //- 存在：
        if (null != os) {
            // 判断已经预约的人数是否大于要更新的最大可预约人数， reverations > 传进来的number数量，则不能更新，要报错
            if (orderSetting.getNumber() < os.getReservations()) {
                // 已经预约的人数高于最大预约人数，不允许
                throw new MyException("最大预约人数不能小已预约人数！");
            }
            // reverations <= 传进来的number数量，则要更新最大可预约数量
            orderSettingDao.updateNumber(orderSetting);
        } else {
            //- 不存在：
            //  - 添加预约设置信息
            orderSettingDao.add(orderSetting);
        }
    }
}
