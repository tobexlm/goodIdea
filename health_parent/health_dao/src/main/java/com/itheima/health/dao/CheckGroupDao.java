package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {

    /**
     * 查询检查组
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 新增检查组
     *
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 按条件分页查询
     *
     * @param queryPageBean
     * @return
     */
    Page<CheckGroup> findPage(QueryPageBean queryPageBean);

    /**
     * 根据id查询检查组信息
     *
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 通过id获取检查项复选框勾选
     *
     * @param id
     * @return
     */
    List<Integer> findCheckgroupIdsBySetmealId(int id);

    /**
     * 更新套餐信息
     * @param checkGroup
     */
    void update(CheckGroup checkGroup);

    /**
     * 删除检查组和检查项的关系
     * @param id
     */
    void deleteCheckGroupAndCheckItem(Integer id);

    /**
     * 添加新关系
     * @param id
     * @param checkitemId
     */
    void addSetmealCheckGroup(@Param("checkgroupId") Integer id, @Param("checkitemId") Integer checkitemId);

    /**
     * 是否被套餐所使用
     * @param id
     * @return
     */
    int QueryCheckgroupRelatedSetmeal(int id);

    /**
     * 删除检查组
     * @param id
     */
    void deleteCheckgroup(int id);
}
