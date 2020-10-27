package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author xlm <tobexlm@163.com>
 * @Date 2020/10/25 20:28
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    /**
     * 新增套餐
     *
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        // 添加套餐信息
        setmealDao.add(setmeal);

        // 获取套餐的id
        Integer setmealId = setmeal.getId();

        // 添加套餐与检查组的关系
        if (null != checkgroupIds) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmealId, checkgroupId);
            }
        }
    }

    /**
     * 分页查询套餐列表
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        // 通用分页拦截器
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        // 用户输入的查询条件
        String queryString = queryPageBean.getQueryString();

        // 用户是否输入查询条件
        if (StringUtil.isNotEmpty(queryString)) {
            // 模糊查询加上"%"
            queryPageBean.setQueryString("%" + queryString + "%");
        }
        // 分页查询
        Page<Setmeal> page = setmealDao.findPage(queryPageBean);
        // 封装查询到的数据rows和总数目total
        return new PageResult<Setmeal>(page.getTotal(), page);
    }

    /**
     * 根据id查询套餐信息
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    /**
     * 通过id查询选中的检查组ids
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckgroupIdsBySetmealId(int id) {
        return setmealDao.findCheckgroupIdsBySetmealId(id);
    }

    /**
     * 更新套餐
     *
     * @param checkgroupIds
     * @param setmeal
     */
    @Override
    @Transactional
    public void update(Integer[] checkgroupIds, Setmeal setmeal) {
        // 先更新套餐信息
        setmealDao.update(setmeal);
        // 删除旧关系
        setmealDao.deleteSetmealAndCheckgroup(setmeal.getId());
        // 添加新关系
        if (checkgroupIds != null) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmeal.getId(), checkgroupId);
            }
        }
    }

    /**
     * 删除套餐
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id){
        // 先判断是否存在订单,存在则不能删除
        int cnt = setmealDao.findOrderCountBySetmealId(id);
        if (cnt > 0) {
            // 该套餐下有正在进行的订单,不能删除
            throw new MyException("已经有订单使用了这个套餐，不能删除！");
        }

        // 先删除与检查项的表关系,然后再删除套餐
        setmealDao.deleteSetmealAndCheckgroup(id);
        setmealDao.deleteById(id);
    }
}
