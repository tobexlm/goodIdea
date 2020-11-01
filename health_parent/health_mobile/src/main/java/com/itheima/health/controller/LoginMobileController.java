package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @Author xlm <tobexlm@163.com>
 * @Date 2020/10/30 0:23
 */
@RestController
@RequestMapping("/login")
public class LoginMobileController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private LoginService loginService;

    /**
     * 登录校验
     */
    @PostMapping("/check")
    public Result check(@RequestBody Map<String, String> loginInfo, HttpServletResponse response) {
        // 获取到前端的参数,手机号和验证码
        String telephone = loginInfo.get("telephone");
        String validateCode = loginInfo.get("validateCode");
        // 拼接登录标识符002_13888888888
        String key_telephone = RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone;
        // 连接Redis
        Jedis jedis = jedisPool.getResource();

        // 获取Redis是否有验证码
        String value_validateCode = jedis.get(key_telephone);
        if (value_validateCode == null) {
            // 没有验证码
            return new Result(false, "验证码已失效,请重新获取");
        }
        if (!validateCode.equals(value_validateCode)) {
            // 不符合
            // 清除验证码
            jedis.del(key_telephone);
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        // 查询是否为注册会员
        Member member = loginService.findByTelephone(telephone);
        if (member == null) {
            // 注册会员
            member = new Member();
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);
            member.setRemark("通过快速登录注册的用户");
            loginService.add(member);
        }
        // 将会员手机号存入cookie
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        // 期限1个月
        cookie.setMaxAge(30 * 24 * 60 * 60);
        // 所有路径都会发送cookie
        cookie.setPath("/");
        // 添加cookie
        response.addCookie(cookie);
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
