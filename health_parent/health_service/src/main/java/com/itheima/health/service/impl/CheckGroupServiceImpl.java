package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author xlm <tobexlm@163.com>
 * @Date 2020/10/25 12:27
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 查询检查组
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    /**
     * 新增检查组
     *
     * @param checkGroup
     */
    @Override
    @Transactional
    public void add(CheckGroup checkGroup) {
        checkGroupDao.add(checkGroup);
    }

    /**
     * 检查组分页查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {

        // 分页拦截器
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        // 是否有查询条件
        if (StringUtil.isNotEmpty(queryPageBean.getQueryString())) {
            // 模糊查询加上"%"
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }

        // 分页查询
        Page<CheckGroup> page = checkGroupDao.findPage(queryPageBean);

        // 封装结果返回
        return new PageResult<CheckGroup>(page.getTotal(), page);
    }

    /**
     * 根据id查询检查组信息
     *
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }

    /**
     * 通过id获取检查项复选框勾选
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckitemIdsByCheckGroupId(int id) {
        return checkGroupDao.findCheckgroupIdsBySetmealId(id);
    }

    /**
     * 更新提交
     * @param checkitemIds
     * @param checkGroup
     */
    @Override
    @Transactional
    public void update(Integer[] checkitemIds, CheckGroup checkGroup) {
        // 先更新套餐信息
        checkGroupDao.update(checkGroup);

        // 删除旧关系
        checkGroupDao.deleteCheckGroupAndCheckItem(checkGroup.getId());

        // 添加新关系
        if (checkitemIds != null) {
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addSetmealCheckGroup(checkGroup.getId(), checkitemId);
            }
        }
    }

    /**
     * 删除检查组
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id) {
        // 是否被套餐所使用
        int cnt = checkGroupDao.QueryCheckgroupRelatedSetmeal(id);
        // 被使用,不可被删除
        if (cnt > 0) {
            throw new MyException("该检查组被套餐所使用,不能删除!");
        }
        // 没使用删除检查组和检查项的关系
        checkGroupDao.deleteCheckGroupAndCheckItem(id);
        // 删除检查组
        checkGroupDao.deleteCheckgroup(id);
    }
}
