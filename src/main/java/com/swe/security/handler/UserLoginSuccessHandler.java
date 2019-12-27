package com.swe.security.handler;

import com.swe.common.config.JWTConfig;
import com.swe.common.util.ResultUtil;
import com.swe.security.entity.SelfUserEntity;
import com.swe.common.util.JWTTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 登录成功处理类
 * @Author cbw
 */
@Slf4j
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * 登录成功返回结果
     * @Author cbw
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        // 组装JWT
        SelfUserEntity selfUserEntity =  (SelfUserEntity) authentication.getPrincipal();
        String token = JWTTokenUtil.create2Token(selfUserEntity);
        // 封装返回参数
        Map<String,Object> resultData = new HashMap<>();
        resultData.put("code","200");
        resultData.put("msg", "登录成功");
        resultData.put("token",token);
        log.info("【登录成功】"+token);
        ResultUtil.responseJson(response,resultData);
    }
}