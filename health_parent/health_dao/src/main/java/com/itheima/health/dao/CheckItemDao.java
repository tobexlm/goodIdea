package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {

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
     *
     * @return
     */
    Page<CheckItem> findPage(QueryPageBean queryPageBean);

}
