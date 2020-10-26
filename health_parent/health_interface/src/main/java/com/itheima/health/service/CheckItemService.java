package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
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
     * 检查项分页条件查询
     *
     * @param queryPageBean
     * @return
     */
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    /**
     * 根据id删除检查项
     *
     * @param id
     */
    void deleteById(int id) throws MyException;

    /**
     * 根据id查询检查项
     *
     * @param id
     * @return
     */
    CheckItem findById(int id);

    /**
     * 更新检查项
     *
     * @param checkItem
     */
    void update(CheckItem checkItem);
}
