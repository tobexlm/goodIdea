package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author xlm <tobexlm@163.com>
 * @Date 2020/10/29 20:01
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    /**
     * 提交预约
     *
     * @param paramsMap
     * @return
     */
    @Override
    @Transactional
    public Order submit(Map<String, String> paramsMap) throws MyException{
        // 预约日期是否格式正确
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date orderDate = null;
        try {
            orderDate = simpleDateFormat.parse(paramsMap.get("orderDate"));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new MyException("日期格式不正确,请重新选择");
        }

        // 所选日期是否可以预约
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
        if (orderSetting == null) {
            throw new MyException("所选日期不能预约,请选择其它日期");
        }

        // 预约是否已排满
        if (orderSetting.getReservations() >= orderSetting.getNumber()) {
            throw new MyException("预约已排满,请选择其它日期");
        }

        // 判断是否重新预约
        String telephone = paramsMap.get("telephone");
        // 通过手机号查询会员信息
        Member member = memberDao.findByTelephone(telephone);

        Order order = new Order();
        // 预约日期
        order.setOrderDate(orderDate);
        // 套餐id
        order.setSetmealId(Integer.parseInt(paramsMap.get("setmealId")));

        if (member != null) {
            // 会员id
            order.setMemberId(member.getId());

            // 判断是否重复预约
            List<Order> orderList = orderDao.findByCondition(order);
            if (orderList != null && orderList.size() > 0) {
                throw new MyException("该套餐已经预约过了,请不要重复预约");
            }
        } else {
            // 不存在会员信息,进行注册
            member = new Member();
            // name 从前端来
            member.setName(paramsMap.get("name"));
            // sex  从前端来
            member.setSex(paramsMap.get("sex"));
            // idCard 从前端来
            member.setIdCard(paramsMap.get("idCard"));
            // phoneNumber 从前端来
            member.setPhoneNumber(telephone);
            // regTime 系统时间
            member.setRegTime(new Date());/**/
            // password 可以不填，也可生成一个初始密码
            member.setPassword("12345678");
            // remark 自动注册
            member.setRemark("由预约而注册上来的");
            //   添加会员
            memberDao.add(member);
            order.setMemberId(member.getId());
        }

        // 可预约
        // 微信预约
        order.setOrderType(paramsMap.get("orderType"));
        // 是否到诊
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        // 预约信息
        orderDao.add(order);

        // 更新已预约人数,更新成功返回1,反之返回0
        int affectedCount = orderSettingDao.editReservationsByOrderDate(orderSetting);
        if (affectedCount == 0) {
            throw new MyException(MessageConstant.ORDER_FULL);
        }
        //5. 返回新添加的订单对象
        return order;
    }

    /**
     * 查询预约信息
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> findById(int id) {
        return orderDao.findById4Detail(id);
    }
}
