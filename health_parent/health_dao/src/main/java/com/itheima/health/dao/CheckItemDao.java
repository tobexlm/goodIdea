package com.itheima.health.dao;

import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: Eric
 * @since: 2020/10/22
 */
public interface CheckItemDao {

    /**
     * 查询检查项
     *
     * @return
     */
    List<CheckItem> findAll();
}
