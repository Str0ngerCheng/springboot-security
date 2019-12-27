package com.swe.security.jwt;

import com.alibaba.fastjson.JSONObject;
import com.swe.common.config.JWTConfig;
import com.swe.common.util.JWTTokenUtil;
import com.swe.security.entity.SelfUserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JWT接口请求校验拦截器
 * 请求接口时会进入这里验证Token是否合法和过期
 * @Author cbw
 */
@Slf4j
public class JWTAuthenticationTokenFilter extends BasicAuthenticationFilter {
    private static Logger log = LoggerFactory.getLogger(JWTTokenUtil.class);

    public JWTAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中JWT的Token
        String tokenHeader = request.getHeader(JWTConfig.tokenHeader);
        if (null!=tokenHeader && tokenHeader.startsWith(JWTConfig.tokenPrefix)) {
            // 截取JWT前缀
            String token = tokenHeader.replace(JWTConfig.tokenPrefix, "");
            // 解析JWT
            SelfUserEntity selfUserEntity =null;
            try {
                Claims claims = JWTTokenUtil.parseToken(token);
                selfUserEntity= JWTTokenUtil.parseClaims(claims);

            }catch(ExpiredJwtException e){
                log.info("Token过期");
                String newToken=JWTTokenUtil.refreshAccessToken(tokenHeader,e.getClaims());//刷新Token
                if(newToken!=null){
                    log.info("生成新的Token");
                    // 截取JWT前缀
                    System.out.println(newToken.replace(JWTConfig.tokenPrefix,""));
                    Claims claims = JWTTokenUtil.parseToken(newToken.replace(JWTConfig.tokenPrefix,""));
                    selfUserEntity=JWTTokenUtil.parseClaims(claims);
                    //添加到响应头中 以供前端存储
                    response.setHeader("newToken", newToken);
                    response.addHeader("Access-Control-Expose-Headers", "newToken");
                    response.setCharacterEncoding("UTF-8");
                }
            } catch(Exception e){
                log.info("Token无效");
            }
            if(selfUserEntity!=null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(selfUserEntity, selfUserEntity.getPassword(), selfUserEntity.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
        return;
    }
}