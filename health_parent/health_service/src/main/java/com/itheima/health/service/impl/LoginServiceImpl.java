package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author xlm <tobexlm@163.com>
 * @Date 2020/10/30 1:59
 */
@Service(interfaceClass = LoginService.class)
public class LoginServiceImpl implements LoginService {

    @Autowired
    private MemberDao memberDao;

    /**
     * 根据手机号码查询会员信息
     *
     * @param telephone
     * @return
     */
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    /**
     * 添加新会员
     *
     * @param member
     */
    @Override
    public void add(Member member) {
        memberDao.add(member);
    }
}
