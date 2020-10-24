package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {

    /**
     * 查询检查项
     *
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 新增检查项
     *
     * @param checkItems
     */
    void add(CheckItem checkItems);


    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

}
