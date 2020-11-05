package com.itheima.health.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author xlm <tobexlm@163.com>
 * @Date 2020/11/3 17:31
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;

    /**
     * 提供用户登录信息 用户名、密码、权限集合
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user != null) {
            // 用户名username
            // 密码
            String password = user.getPassword();
            /*
              获取权限集合
             */
            List<GrantedAuthority> authorities = new ArrayList<>();
            // 用户拥有的角色
            Set<Role> roles = user.getRoles();
            if (roles != null) {
                SimpleGrantedAuthority sga;
                for (Role role : roles) {
                    sga = new SimpleGrantedAuthority(role.getKeyword());
                    authorities.add(sga);
                    // 角色拥有的权限
                    Set<Permission> permissions = role.getPermissions();
                    if (permissions != null) {
                        for (Permission permission : permissions) {
                            sga = new SimpleGrantedAuthority(permission.getKeyword());
                            authorities.add(sga);
                        }
                    }
                }
            }
            return new org.springframework.security.core.userdetails.User(username, password, authorities);
        }
        // 限制访问
        return null;
    }
}
