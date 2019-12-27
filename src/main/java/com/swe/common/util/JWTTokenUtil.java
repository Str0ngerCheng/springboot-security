package com.swe.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.swe.common.config.JWTConfig;
import com.swe.security.entity.SelfUserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * JWT工具类
 *
 * 问题1：如何处理过期得Token？一般设置accessToken和refreshToken，accessToken过期时间为1-2小时，保存在LocalStore；refreshToken保存在Redis，过期时间为15天。如果accessToken过期则访问refreshToken
 * refreshToken没过期就重新生成新的accessToken发送到前端，否则跳转到登陆界面重新登陆
 *
 * 问题2：强制登出如何处理Token？将accessToken加入黑名单，黑名单存在Redis中，验证accessToken时若Token在黑名单中就重新登陆
 * 问题3：并发问题，多个请求同时携带旧的（已过期）accessToken访问接口，该如何处理？
 * @Author cbw
 */
@Slf4j
public class JWTTokenUtil {
    @Autowired
    protected RedisTemplate redisTemplate;

    private volatile static String newToken=null;
    /**
     * 私有化构造器
     */
    private JWTTokenUtil(){}

    /**
     * 生成AccessToken
     * @Param  selfUserEntity 用户安全实体
     * @Return Token
     */
    public static String createAccessToken(SelfUserEntity selfUserEntity){
        // 登陆成功生成JWT
        String token = Jwts.builder()
                // 放入用户名和用户ID
                .setId(selfUserEntity.getUserId().toString())
                // 主题
                .setSubject(selfUserEntity.getUsername())
                // 签发时间
                .setIssuedAt(new Date())
                // 签发者
                .setIssuer("cbw")
                // 自定义属性 放入用户拥有权限
                .claim("authorities", JSON.toJSONString(selfUserEntity.getAuthorities()))
                // 失效时间
                .setExpiration(new Date(System.currentTimeMillis() + JWTConfig.expiration))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, JWTConfig.secret)
                .compact();
        return token;
    }
    /**
     * 生成AccessToken和RefreshToken  RefreshToken=当前时间戳+AccessToken
     * @Param  selfUserEntity 用户安全实体
     * @Return 返回AccessToken
     */
    public static String create2Token(SelfUserEntity selfUserEntity){
        // 登陆成功生成AccessToken
        String accessToken = JWTConfig.tokenPrefix + createAccessToken(selfUserEntity);
        // 登陆成功生成RefreshToken
        String refreshToken = System.currentTimeMillis()+":"+accessToken;
        RedisUtil.set(selfUserEntity.getUserId().toString(),refreshToken,JWTConfig.refreshExpiration);
        return accessToken;
    }
    /**
     * 解析Token
     * @Param  token 前端获取的token
     * @Return 返回Claims信息
     */
    public static Claims parseToken(String token){
        Claims claims = Jwts.parser()
                    .setSigningKey(JWTConfig.secret)
                    .parseClaimsJws(token)
                    .getBody();
        return claims;
    }

    /**
     * 解析Claims
     * @Param  Claims 解析Token后得到
     * @Return 返回用户信息 SelfUserEntity
     */
    public static SelfUserEntity parseClaims(Claims claims){
        SelfUserEntity selfUserEntity = new SelfUserEntity();
        String username = claims.getSubject();
        String userId = claims.getId();
        if (RedisUtil.get(userId)==null)//此时代表由于登出操作，Redis删除了相应的Token使得该Token无效
            return null;
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(userId)) {
            // 获取角色
            List<GrantedAuthority> authorities = new ArrayList<>();
            String authority = claims.get("authorities").toString();
            if (!StringUtils.isEmpty(authority)) {
                List<Map<String, String>> authorityMap = JSONObject.parseObject(authority, List.class);
                for (Map<String, String> role : authorityMap) {
                    if (!StringUtils.isEmpty(role)) {
                        authorities.add(new SimpleGrantedAuthority(role.get("authority")));
                    }
                }
            }
            //组装参数
            selfUserEntity.setUsername(username);
            selfUserEntity.setUserId(Long.parseLong(userId));
            selfUserEntity.setAuthorities(authorities);

        }
        return selfUserEntity;
    }

    /**
     * 刷新Token，对于过期的AccessToken，如果 Refresh没过期，同时判断当前过期token和redis中的token是否一致。若一致则刷新生成新的AccessToken，并将其更新到redis中；
     * 若不一致则表示AccessToken已经刷新，该过期token废弃（类似加入黑名单）
     * 作用：防止并发情况下，多个请求线程携带同一个过期token生成多个AccessToken
     * @Param  Claims
     * @Return 返回更新得Token
     */
    public static String refreshAccessToken(String token,Claims claims){
        SelfUserEntity selfUserEntity = parseClaims(claims);
        String userId = selfUserEntity.getUserId().toString();
        //判断Redis中的RefreshToken是否过期，没有过期则刷新AccessToken,否则返回空
        String value=RedisUtil.get(userId).toString();
        if(value!=null) {
            String issueDate=value.split(":")[0];
            String oldToken=value.split(":")[1];
            if(token.equals(oldToken)) {//双重检测锁，防止并发情况下生成多个Token，多次写入Redis
                synchronized (newToken) {
                    oldToken=RedisUtil.get(userId).toString().split(":")[1];
                    if (token.equals(oldToken)) {
                        newToken = JWTConfig.tokenPrefix+createAccessToken(selfUserEntity);
                        String refreshToken=System.currentTimeMillis()+":"+newToken;
                        RedisUtil.set(userId,refreshToken,JWTConfig.refreshExpiration-(System.currentTimeMillis()-Long.parseLong(issueDate)));
                    }
                }

            }
            return newToken;
        }
        return null;
    }

    /**
     * Description: 使该Token失效，用于登出操作，或后台强制收回权限
     *
     */
    public static void inValidToken(String token) {
        Claims claims =parseToken(token);
        String userId = claims.getId();
        RedisUtil.delete(userId);
    }
}