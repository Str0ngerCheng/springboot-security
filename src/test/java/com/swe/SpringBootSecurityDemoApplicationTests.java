package com.swe;

import com.swe.core.entity.User;
import com.swe.core.entity.UserRole;
import com.swe.core.service.UserRoleService;
import com.swe.core.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootSecurityDemoApplicationTests {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRoleService userRoleService;


    /**
     * 注册用户
     */
    @Test
    public void contextLoads() {
        User user=new User();
        user.setNickname("cbw");
        user.setPassword(bCryptPasswordEncoder.encode("cbw"));
        user.setCampus(0);
        user.setPhone("12345678910");
        user.setSno("2018206190018");
        user.setSex(false);
        user.setHead(null);
        user.setStatus("NORMAL");
        userService.save(user);

        UserRole sysUserRoleEntity = new UserRole();
        sysUserRoleEntity.setRoleId(2L);
        sysUserRoleEntity.setUserId(user.getId());
        userRoleService.save(sysUserRoleEntity);

      /*  // 注册用户
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUsername("cbw");
        sysUserEntity.setPassword(bCryptPasswordEncoder.encode("swe123"));
        // 设置用户状态
        sysUserEntity.setStatus("NORMAL");
        sysUserService.save(sysUserEntity);
        // 分配角色 1:ADMIN 2:USER
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        sysUserRoleEntity.setRoleId(2L);
        sysUserRoleEntity.setUserId(sysUserEntity.getUserId());
        sysUserRoleService.save(sysUserRoleEntity);*/
    }

}