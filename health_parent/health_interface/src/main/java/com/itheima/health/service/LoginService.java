package com.itheima.health.service;

import com.itheima.health.pojo.Member;

public interface LoginService {
    /**
     * 根据手机号码查询会员信息
     *
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 添加新会员
     *
     * @param member
     */
    void add(Member member);
}
