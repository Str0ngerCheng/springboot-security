package com.sans.controller;
import com.sans.core.entity.LoginParam;
import com.sans.core.entity.SysUserEntity;
import com.sans.core.entity.SysUserRoleEntity;
import com.sans.core.service.SysUserRoleService;
import com.sans.core.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhaoxinguo on 2018/06/05.
 */
@RestController
@RequestMapping("/users")
public class RegisterController{
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 注册用户 默认开启白名单
     * @param user
     */
    @PostMapping("/signup")
    @ResponseBody
    public String signup(@RequestBody LoginParam user) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity=sysUserService.selectUserByName(user.getUsername());
        if(null != sysUserEntity){
            log.info("该用户名已经被注册");
            return "注册失败";
        }
        sysUserEntity.setUsername(user.getUsername());
        sysUserEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        // 设置用户状态
        sysUserEntity.setStatus("NORMAL");
        sysUserService.save(sysUserEntity);
        // 分配角色 1:ADMIN 2:USER
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        sysUserRoleEntity.setRoleId(2L);
        sysUserRoleEntity.setUserId(sysUserEntity.getUserId());
        sysUserRoleService.save(sysUserRoleEntity);
        return "注册成功";
    }

}
