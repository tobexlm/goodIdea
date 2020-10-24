package com.itheima.health.service;

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
public interface CheckItemService {

    /**
     * 查询检查项
     *
     * @Return
     */
    List<CheckItem> findAll();
}
