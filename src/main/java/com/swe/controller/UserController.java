package com.swe.controller;

import com.swe.common.util.ResultUtil;
import com.swe.core.entity.User;
import com.swe.core.service.PermissionService;
import com.swe.core.service.UserService;
import com.swe.security.entity.SelfUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 普通用户
 * @Author cbw
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserService userService;

    /**
     * 用户端信息
     * @Return Map<String,Object> 返回数据MAP
     */
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public Map<String,Object> userLogin(){
        Map<String,Object> result = new HashMap<>();
        SelfUserEntity userDetails = (SelfUserEntity) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        result.put("title","用户端信息");
        result.put("data",userDetails);
        return ResultUtil.resultSuccess(result);
    }

    /**
     * 拥有USER角色和sys:user:info权限可以访问
     * @Return Map<String,Object> 返回数据MAP
     */
    @PreAuthorize("hasRole('USER') or hasPermission('/user/list','sys:user:info')")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Map<String,Object> sysMenuEntity(){
        Map<String,Object> result = new HashMap<>();
        List<User> userList = userService.list();
        result.put("title","拥有USER角色和sys:user:info权限可以访问");
        result.put("data",userList);
        return ResultUtil.resultSuccess(result);
    }

}
