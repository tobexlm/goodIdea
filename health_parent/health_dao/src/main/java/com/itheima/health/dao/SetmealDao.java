package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealDao {
    /**
     * 添加套餐
     *
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 添加套餐与检查组的关系
     *
     * @param setmealId
     * @param checkgroupId
     */
    void addSetmealCheckGroup(@Param("setmealId") Integer setmealId,
                              @Param("checkgroupId") Integer checkgroupId);

    /**
     * 分页查询套餐列表
     *
     * @param queryPageBean
     * @return
     */
    Page<Setmeal> findPage(QueryPageBean queryPageBean);

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
     * @param setmeal
     */
    void update(Setmeal setmeal);

    /**
     * 删除套餐与检查组的表关系
     *
     * @param id
     */
    void deleteSetmealAndCheckgroup(Integer id);

    /**
     * 删除套餐
     *
     * @param id
     */
    void deleteById(int id);

    /**
     * 查询套餐下是否有进行的订单
     *
     * @param id
     * @return
     */
    int findOrderCountBySetmealId(int id);

    /**
     * 查询所有套餐信息
     *
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 查询套餐详情
     *
     * @param id
     * @return
     */
    Setmeal findDetailById(int id);

    /**
     * 查出数据库中所有图片
     *
     * @return
     */
    List<String> findImgs();
}
