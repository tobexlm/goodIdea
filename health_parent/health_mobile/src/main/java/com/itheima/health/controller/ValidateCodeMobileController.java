package com.itheima.health.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.util.SMSUtils;
import com.itheima.health.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static com.itheima.health.util.SMSUtils.VALIDATE_CODE;

/**
 * @Author xlm <tobexlm@163.com>
 * @Date 2020/10/29 18:23
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeMobileController {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 体检预约发送验证码
     *
     * @return
     */
    @PostMapping("/send4Order")
    public Result send4Order(String telephone) {
        // 体检预约验证码 + "_" + 用户手机号,001_13888888888
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        Jedis jedis = jedisPool.getResource();

        // 判断Redis里是否有该手机号的验证码
        if (jedis.get(key) == null) {
            // 不存在
            // 生成6位验证码
            Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
            try {
                // 发送给用户
                SMSUtils.sendShortMessage(VALIDATE_CODE, SMSUtils.PHONE_NUMBER, validateCode + "");
                // 存入Redis,有效5分钟
                jedis.setex(key, 5 * 60, validateCode + "");
            } catch (ClientException e) {
                e.printStackTrace();
                // 发送失败
                return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
            }
            // 返回发送成功
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }
        // Redis有该用户的验证码,提示用户
        return new Result(true, MessageConstant.SENT_VALIDATECODE);
    }

    /**
     * 登录发送验证码
     */
    @PostMapping("/send4Login")
    public Result send4Login(String telephone) {
        // 拼接标识码,这是存入Redis中的key(002_13888888888)
        String key = RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone;
        Jedis jedis = jedisPool.getResource();

        // 判断Redis里是否有该手机号的验证码
        if (jedis.get(key) == null) {
            // 不存在
            // 生成6位验证码
            Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
            try {
                // 发送给用户
                SMSUtils.sendShortMessage(VALIDATE_CODE, SMSUtils.PHONE_NUMBER, validateCode + "");
                // 存入Redis,有效5分钟
                jedis.setex(key, 5 * 60, validateCode + "");
            } catch (ClientException e) {
                e.printStackTrace();
                // 发送失败
                return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
            }
            // 返回发送成功
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }
        // Redis有该用户的验证码,提示用户
        return new Result(true, MessageConstant.SENT_VALIDATECODE);
    }
}
