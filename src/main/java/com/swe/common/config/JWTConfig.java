package com.swe.common.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置类
 * @Author cbw
 */
@Getter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTConfig {
    /**
     * 密钥KEY
     */
    public static String secret;
    /**
     * TokenKey
     */
    public static String tokenHeader;
    /**
     * Token前缀字符
     */
    public static String tokenPrefix;
    /**
     * accessToken过期时间
     */
    public static Integer expiration;
    /**
     * refreshToken过期时间
     */
    public static Integer refreshExpiration;
    /**
     * 不需要认证的接口
     */
    public static String antMatchers;


    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public void setExpiration(Integer expiration) {
        this.expiration = expiration*60*60*1000;
    }
    public  void setRefreshExpiration(Integer refreshExpiration) { JWTConfig.refreshExpiration = refreshExpiration*60*60*1000; }

    public void setAntMatchers(String antMatchers) {
        this.antMatchers = antMatchers;
    }


}