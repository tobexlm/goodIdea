package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {

    private static final Logger log = LoggerFactory.getLogger(CheckGroupController.class);

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 查询检查组
     *
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll() {
        List<CheckGroup> checkGroups = checkGroupService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroups);
    }

    /**
     * 新增检查组
     *
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup) {
        checkGroupService.add(checkGroup);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 检查组分页查询
     *
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, pageResult);
    }

    /**
     * 根据id查询检查组信息
     *
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id) {
        CheckGroup checkGroup = checkGroupService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
    }

    /**
     * 通过id获取检查项复选框勾选
     *
     * @param id
     * @return
     */
    @GetMapping("/findCheckitemIdsByCheckGroupId")
    public Result findCheckitemIdsByCheckGroupId(int id) {
        List<Integer> list = checkGroupService.findCheckitemIdsByCheckGroupId(id);
        return new Result(true, "查询检查项id成功", list);
    }

    /**
     * 更新提交
     *
     * @param checkitemIds
     * @param checkGroup
     * @return
     */
    @PostMapping("/update")
    public Result update(Integer[] checkitemIds, @RequestBody CheckGroup checkGroup) {
        checkGroupService.update(checkitemIds, checkGroup);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /**
     * 删除检查组
     * @param id
     * @return
     */
    @GetMapping("/deleteById")
    public Result deleteById(int id) {
        checkGroupService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
}
