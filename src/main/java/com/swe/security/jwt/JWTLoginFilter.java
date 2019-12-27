package com.swe.security.jwt;

import com.alibaba.fastjson.JSONObject;
import com.swe.common.util.GetRequestJsonUtil;
import com.swe.security.entity.SelfUserEntity;
import com.swe.security.handler.UserLoginFailureHandler;
import com.swe.security.handler.UserLoginSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法
 * attemptAuthentication ：接收并解析用户凭证。
 * successfulAuthentication ：用户成功登录后，这个方法会被调用，我们在这个方法里生成token。
 */

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager,UserLoginSuccessHandler userLoginSuccessHandler,UserLoginFailureHandler userLoginFailureHandler) {

        this.authenticationManager = authenticationManager;
        this.setFilterProcessesUrl("/login/userLogin");
        this.setAuthenticationSuccessHandler(userLoginSuccessHandler);
        this.setAuthenticationFailureHandler(userLoginFailureHandler);


    }
    // 接收并解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        String username="";
        String password="";
        try {
            JSONObject json = GetRequestJsonUtil.getRequestJsonObject(req);
            username=json.getString("username");
            password=json.getString("password");
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>()));
        return authentication;

    }
}
