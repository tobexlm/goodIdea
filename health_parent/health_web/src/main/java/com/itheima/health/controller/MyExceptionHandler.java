package com.itheima.health.controller;

import com.itheima.health.entity.Result;
import com.itheima.health.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 * 统一异常处理
 * 【注意】，这个类必须进入spring容器，要在扫包的目录下即可
 * </p>
 * 相当try
 */
@RestControllerAdvice
public class MyExceptionHandler {

    /**
     * info: 记录执行的过程
     * debug: 记录执行过程中重要的key
     * error: 记录异常信息
     */
    private static final Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);

    /**
     * 捕获异常
     * catch(MyException e)
     */
    @ExceptionHandler(MyException.class)
    public Result handleMyException(MyException e) {
        return new Result(false, e.getMessage());
    }

    /**
     * 密码错误
     *
     * @param he
     * @return
     */
    @ExceptionHandler(BadCredentialsException.class)
    public Result handBadCredentialsException(BadCredentialsException he) {
        return handleUserPassword();
    }

    /**
     * 用户名不存在
     *
     * @param he
     * @return
     */
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public Result handInternalAuthenticationServiceException(InternalAuthenticationServiceException he) {
        return handleUserPassword();
    }

    private Result handleUserPassword() {
        return new Result(false, "用户名或密码错误");
    }

    /**
     * 用户名不存在
     *
     * @param he
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result handAccessDeniedException(AccessDeniedException he) {
        return new Result(false, "没有权限");
    }

    /**
     * 未知异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        // e.printStackTrace(); System.out.println() // out输出流 硬件输出设备 占用大量系统资源
        log.error("发生未知异常", e);
        return new Result(false, "发生未知异常，请联系管理员");
    }
}
