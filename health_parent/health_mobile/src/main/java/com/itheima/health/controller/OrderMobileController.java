package com.itheima.health.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Order;
import com.itheima.health.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @Author xlm <tobexlm@163.com>
 * @Date 2020/10/29 19:20
 */
@RestController
@RequestMapping("/order")
public class OrderMobileController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 提交预约
     *
     * @return
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String, String> paramsMap) {
        // 1. 验证码校验
        // 用户手机号,redis的key
        String telephone = paramsMap.get("telephone");
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;

        // 判断Redis里是否有验证码,没有验证码,要求用户重新获取
        Jedis jedis = jedisPool.getResource();
        String code = jedis.get(key);
        if (StringUtils.isEmpty(code)) {
            // 没有验证码,重新发送
            return new Result(false, MessageConstant.REACQUIREL_CODE);
        }
        // 有验证码,将用户输入的和Redis存入的进行比对
        String validateCode = paramsMap.get("validateCode");
        if (!validateCode.equals(code)) {
            // 不正确,请重新输入
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        // 移除Redis验证码,防止重复输入破解验证码!!!开发中可以注释
        // jedis.del(key);

        // 设置预约的类型
        paramsMap.put("orderType", Order.ORDERTYPE_WEIXIN);
        // 显示预约成功的信息
        Order order = orderService.submit(paramsMap);
        return new Result(true, MessageConstant.ORDER_SUCCESS, order);
    }

    /**
     * 查询预约信息
     *
     * @return
     */
    @PostMapping("/findById")
    public Result findById(int id) {
        // 调用服务查询订单信息
        Map<String,Object> orderInfo = orderService.findById(id);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,orderInfo);
    }

}
