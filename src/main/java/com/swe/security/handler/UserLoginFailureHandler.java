package com.swe.security.handler;


import com.swe.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 登录失败处理类
 * @Author cbw
 */
@Slf4j
@Component("userLoginFailureHandler")
public class UserLoginFailureHandler implements AuthenticationFailureHandler {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * 登录失败返回结果
     * @Author cbw
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception){
        // 这些对于操作的处理类可以根据不同异常进行不同处理
        if (exception instanceof UsernameNotFoundException){
            log.info("【登录失败】"+exception.getMessage());
            ResultUtil.responseJson(response,ResultUtil.resultCode(500,"用户名不存在"));
        }
        if (exception instanceof LockedException){
            log.info("【登录失败】"+exception.getMessage());
            ResultUtil.responseJson(response,ResultUtil.resultCode(500,"用户被冻结"));
        }
        if (exception instanceof BadCredentialsException){
            System.out.println("【登录失败】"+exception.getMessage());
            log.info("【登录失败】"+exception.getMessage());
            ResultUtil.responseJson(response,ResultUtil.resultCode(500,"用户名密码不正确"));
        }
        ResultUtil.responseJson(response,ResultUtil.resultCode(500,"登录失败"));
    }
}