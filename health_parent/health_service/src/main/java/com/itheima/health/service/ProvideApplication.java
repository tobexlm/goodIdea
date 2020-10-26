package com.itheima.health.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * dubbo服务注册(provide)
 */

public class ProvideApplication {
    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("applicationContext-service.xml");
        // 输入阻塞流
        System.in.read();
    }
}