package com.sans.security.jwt;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sans.common.util.GetRequestJsonUtils;
import com.sans.core.entity.LoginParam;
import com.sans.security.handler.UserLoginFailureHandler;
import com.sans.security.handler.UserLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法
 * attemptAuthentication ：接收并解析用户凭证。
 * successfulAuthentication ：用户成功登录后，这个方法会被调用，我们在这个方法里生成token。
 */

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {
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
            JSONObject json = GetRequestJsonUtils.getRequestJsonObject(req);
            username=json.getString("username");
            password=json.getString("password");
            System.out.println(password);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password,
                        new ArrayList<>())
        );

    }
}
