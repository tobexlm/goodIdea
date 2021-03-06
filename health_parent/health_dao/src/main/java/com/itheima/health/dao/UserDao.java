package com.itheima.health.dao;

import com.itheima.health.pojo.User;

public interface UserDao {

    /**
     * 根据登陆用户名称查询用户权限信息
     * @return
     */
    User findByUsername(String username);
}
