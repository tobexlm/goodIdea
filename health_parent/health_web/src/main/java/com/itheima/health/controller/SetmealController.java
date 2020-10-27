package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.CheckGroupService;
import com.itheima.health.service.SetmealService;
import com.itheima.health.util.QiNiuUtils;
import org.apache.zookeeper.data.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    // 日志
    private static final Logger log = LoggerFactory.getLogger(SetmealController.class);

    @Reference
    private SetmealService setmealService;

    /**
     * 图片上传
     *
     * @param imgFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        // 获取图片名
        String originalFilename = imgFile.getOriginalFilename();
        // 截取到后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 生成唯一文件名(随机识别码和图片名组合)
        String fileName = UUID.randomUUID() + suffix;

        try {
            // 七牛云上传文件
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), fileName);

            // 响应到前端
            Map<String, String> map = new HashMap<>();
            // 文件名,七牛云域名
            map.put("imgName", fileName);
            map.put("domain", QiNiuUtils.DOMAIN);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, map);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }

    /**
     * 新增套餐
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        // 调用service
        setmealService.add(setmeal, checkgroupIds);
        // 响应
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页查询套餐列表
     *
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Setmeal> pageResult = setmealService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, pageResult);
    }

    /**
     * 根据id查询套餐信息
     *
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id) {
        // setmeal
        Setmeal setmeal = setmealService.findById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("setmeal", setmeal);
        map.put("domain", QiNiuUtils.DOMAIN);

        // 响应
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, map);
    }

    /**
     * 通过id查询选中的检查组ids
     *
     * @param id
     * @return
     */
    @GetMapping("/findCheckgroupIdsBySetmealId")
    public Result findCheckgroupIdsBySetmealId(int id) {
        List<Integer> checkgroupIds = setmealService.findCheckgroupIdsBySetmealId(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkgroupIds);
    }

    /**
     * 更新套餐
     *
     * @param checkgroupIds
     * @param setmeal
     * @return
     */
    @PostMapping("/update")
    public Result update(Integer[] checkgroupIds, @RequestBody Setmeal setmeal) {
        setmealService.update(checkgroupIds, setmeal);
        return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
    }

    /**
     * 删除套餐
     *
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id) {
        setmealService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }
}
