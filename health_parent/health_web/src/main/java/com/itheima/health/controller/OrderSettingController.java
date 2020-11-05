package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.util.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author xlm <tobexlm@163.com>
 * @Date 2020/10/31 0:08
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 批量导入
     *
     * @param excelFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        try {
            // 读取Excel内容
            List<String[]> strings = POIUtils.readExcel(excelFile);
            // 转成list<ordersettring>
            List<OrderSetting> orderSettingList = new ArrayList<OrderSetting>();

            // 日期格式解析
            SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            Date orderDate = null;
            OrderSetting os = null;
            for (String[] dataArr : strings) {
                orderDate = sdf.parse(dataArr[0]);
                Integer number = Integer.valueOf(dataArr[1]);
                os = new OrderSetting(orderDate, number);
                orderSettingList.add(os);
            }
            // 调用业务
            orderSettingService.add(orderSettingList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    /**
     * 根据月份查询预约信息
     *
     * @param month
     * @return
     */
    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month) {
        List<Map<String, Integer>> data = orderSettingService.getOrderSettingByMonth(month);
        return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, data);
    }

    /**
     * 预约设置
     *
     * @return
     */
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        orderSettingService.editNumberByDate(orderSetting);
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }
}
