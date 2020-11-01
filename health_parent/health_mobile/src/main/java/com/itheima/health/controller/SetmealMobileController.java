package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.util.QiNiuUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author xlm <tobexlm@163.com>
 * @Date 2020/10/28 10:16
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {

    @Reference
    private SetmealService setmealService;

    /**
     * 查询所有套餐
     *
     * @return
     */
    @GetMapping("/getSetmeal")
    public Result getSetmeal() {
        // 获取所有套餐信息
        List<Setmeal> setmeals = setmealService.findAll();

        // 拼接图片全路径DOMAIN+img
        setmeals.forEach(setmeal -> {
            setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        });

        return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, setmeals);
    }

    /**
     * 查询套餐详情
     *
     * @param id
     * @return
     */
    @GetMapping("/findDetailById")
    public Result findDetailById(int id) {
        // 套餐信息(检查组(检查项))
        Setmeal setmeal = setmealService.findDetailById(id);
        // 添加图片链接
        setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
    }

    /**
     * 根据套餐查询信息
     *
     * @return
     */
    @PostMapping("/findById")
    public Result findById(int id) {
        // 套餐信息(检查组(检查项))
        Setmeal setmeal = setmealService.findDetailById(id);
        // 添加图片链接
        setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
    }
}
