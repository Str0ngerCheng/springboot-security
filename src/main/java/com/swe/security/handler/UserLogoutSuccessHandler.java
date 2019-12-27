package com.swe.security.handler;

import com.swe.common.config.JWTConfig;
import com.swe.common.util.JWTTokenUtil;
import com.swe.common.util.ResultUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 登出成功处理类
 * @Author cbw
 */
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {
    /**
     * 用户登出返回结果
     * 这里应该让前端清除掉Token
     * @Author cbw
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        String tokenHeader = request.getHeader(JWTConfig.tokenHeader);
        JWTTokenUtil.inValidToken(tokenHeader.replace(JWTConfig.tokenPrefix, ""));
        Map<String,Object> resultData = new HashMap<>();
        resultData.put("code","200");
        resultData.put("msg", "登出成功,Token已失效");
        SecurityContextHolder.clearContext();
        ResultUtil.responseJson(response,ResultUtil.resultSuccess(resultData));
    }
}