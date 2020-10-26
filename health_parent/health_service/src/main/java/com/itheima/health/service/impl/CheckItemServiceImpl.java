package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.apache.zookeeper.data.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 查询检查项
     *
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    /**
     * 新增检查项
     *
     * @param checkItems
     */
    @Override
    @Transactional
    public void add(CheckItem checkItems) {
        checkItemDao.add(checkItems);
    }

    /**
     * 检查项分页条件查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        // 分页拦截器
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 模糊查询
        // 查询条件非空判断
        if (StringUtil.isNotEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 查询
        Page<CheckItem> page = checkItemDao.findPage(queryPageBean);

        // 封装返回
        return new PageResult<CheckItem>(page.getTotal(), page);
    }

    /**
     * 根据id删除检查项
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id) {
        /**
         * 检查项和检查组是关系表,用户点击删除后,
         * 需查询数据库中检查组下是否有该检查项,如果有则不能删除,提示用户
         */
        // 查询删除的检查项是否被检查组使用
        int cnt = checkItemDao.findCheckGroupCountByCheckItem(id);
        // 如果被使用则不能被删除
        if (cnt > 0) {
            throw new MyException("已经有检查组使用了这个检查项,不能被删除!");
        }
        // 删除检查项与检查组的关系
        checkItemDao.deleteCheckGroupCheckItemConcern(id);
        // 根据id删除检查项
        checkItemDao.deleteById(id);
    }

    /**
     * 根据id查询检查项
     *
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(int id) {
       return checkItemDao.findById(id);
    }

    /**
     * 更新检查项
     *
     * @param checkItem
     */
    @Override
    @Transactional
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }
}
