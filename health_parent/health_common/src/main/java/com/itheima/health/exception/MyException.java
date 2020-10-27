package com.itheima.health.exception;

/**
 * 自定异常：
 * 1. 区分系统与业务异常
 * 2. 给用户友好的提示信息
 * 3. 终止已知不符合业务逻辑代码的继续执行
 *
 * @Author xlm <tobexlm@163.com>
 * @Date 2020/10/26 2:26
 */

public class MyException extends RuntimeException {
    public MyException(String message) {
        super(message);
    }
}
