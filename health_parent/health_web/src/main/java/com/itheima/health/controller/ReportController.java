package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.entity.Result;
import com.itheima.health.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Author xlm <tobexlm@163.com>
 * @Date 2020/11/1 10:17
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @GetMapping("/getMemberReport")
    public Result getMemberReport() {
        // 产生12个月的数据
        List<String> months = new ArrayList<>();
        // 使用日历
        Calendar car = Calendar.getInstance();
        // 年-1
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        car.add(Calendar.YEAR, -1);
        // 遍历12次,依次加1个月
        for (int i = 0; i < 12; i++) {
            car.add(Calendar.DAY_OF_MONTH, 1);
            months.add(sdf.format(car.getTime()));
        }
        List<String> memberCount = memberService.getMemberReport(months);

        return null;
    }
}
