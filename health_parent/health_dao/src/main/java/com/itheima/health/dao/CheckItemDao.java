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
     * 检查项分页条件查询
     *
     * @return
     */
    Page<CheckItem> findPage(QueryPageBean queryPageBean);

    /**
     * 查询删除的检查项是否被检查组使用
     *
     * @param id
     * @return
     */
    int findCheckGroupCountByCheckItem(int id);

    /**
     * 删除检查项与检查组的关系
     *
     * @param id
     */
    void deleteCheckGroupCheckItemConcern(int id);

    /**
     * 根据id删除检查项
     *
     * @param id
     */
    void deleteById(int id);

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
