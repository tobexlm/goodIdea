package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

public interface SetmealService {

    /**
     * 新增套餐
     *
     * @param setmeal
     * @param checkgroupIds
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 分页查询套餐列表
     *
     * @param queryPageBean
     * @return
     */
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    /**
     * 根据id查询套餐信息
     *
     * @param id
     * @return
     */
    Setmeal findById(int id);

    /**
     * 通过id查询选中的检查组ids
     *
     * @param id
     * @return
     */
    List<Integer> findCheckgroupIdsBySetmealId(int id);

    /**
     * 更新套餐
     *
     * @param checkgroupIds
     * @param setmeal
     */
    void update(Integer[] checkgroupIds, Setmeal setmeal);

    /**
     * 删除套餐
     *
     * @param id
     */
    void deleteById(int id) throws MyException;
}
