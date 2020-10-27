package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {

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
     * 检查组分页查询
     *
     * @param queryPageBean
     * @return
     */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

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
    List<Integer> findCheckitemIdsByCheckGroupId(int id);

    /**
     * 更新提交
     * @param checkitemIds
     * @param checkGroup
     */
    void update(Integer[] checkitemIds, CheckGroup checkGroup);

    /**
     * 删除检查组
     * @param id
     */
    void deleteById(int id) throws MyException;
}
