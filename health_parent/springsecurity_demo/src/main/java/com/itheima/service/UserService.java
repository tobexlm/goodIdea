package com.itheima.service;

import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author xlm <tobexlm@163.com>
 * @Date 2020/11/2 15:56
 */

public class UserService implements UserDetailsService {

    /**
     * 获取用户登录信息
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*
            模拟在数据库查询用户信息
            授权的参数:用户名,密码,角色,权限
            String username,
            String password,
			Collection<? extends GrantedAuthority> authorities
        */
        com.itheima.health.pojo.User UserEnter = findByUsername(username);
        if (UserEnter != null) {
            /*
                授权(用户名密码,以及权限集合)
             */
            // 权限集合
            List<GrantedAuthority> authorities = new ArrayList<>();
            /*
                遍历用户的角色和权限
             */
            // 用户拥有的角色
            Set<Role> roles = UserEnter.getRoles();
            if (roles != null) {
                SimpleGrantedAuthority sga;
                for (Role role : roles) {
                    // 授予角色
                    sga = new SimpleGrantedAuthority(role.getKeyword());
                    authorities.add(sga);
                    // 授予权限
                    Set<Permission> permissions = role.getPermissions();
                    if (permissions != null) {
                        for (Permission permission : permissions) {
                            sga = new SimpleGrantedAuthority(permission.getKeyword());
                            authorities.add(sga);
                        }
                    }
                }
            }
            return new User(username, UserEnter.getPassword(), authorities);
        }
        return null;
    }

    /**
     * 这个用户admin/admin, 有ROLE_ADMIN角色，角色下有ADD_CHECKITEM权限
     * 假设从数据库查询
     *
     * @param username
     * @return
     */
    private com.itheima.health.pojo.User findByUsername(String username) {
        if ("admin".equals(username)) {
            com.itheima.health.pojo.User user = new com.itheima.health.pojo.User();
            user.setUsername("admin");
            // 使用密文，删除{noop}
            user.setPassword("$2a$10$C2I8PHWnBtqMJlvKD7DsCuP9Kl4uQT4TIqBTgda1y/Pekp6Tb/4GO");

            // 角色
            Role role = new Role();
            role.setKeyword("ROLE_ADMIN");

            // 权限
            Permission permission = new Permission();
            permission.setKeyword("ADD_CHECKITEM");

            // 给角色添加权限
            role.getPermissions().add(permission);

            // 把角色放进集合
            Set<Role> roleList = new HashSet<Role>();
            roleList.add(role);

            role = new Role();
            role.setKeyword("ABC");
            roleList.add(role);

            // 设置用户的角色
            user.setRoles(roleList);
            return user;
        }
        return null;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 加密密码
        //System.out.println(encoder.encode("1234"));
        // 校验密码, 第1个参数为明文，第2个为密文
        System.out.println(encoder.matches("1234", "$2a$10$C2I8PHWnBtqMJlvKD7DsCuP9Kl4uQT4TIqBTgda1y/Pekp6Tb/4GO"));
        System.out.println(encoder.matches("1234", "$2a$10$IfPkaV5WRkaaoDODWPLU9uxQgt3qzfVUj1PxnzNPyiY.C7ycQRvAm"));
        System.out.println(encoder.matches("1234", "$2a$10$u/BcsUUqZNWUxdmDhbnoeeobJy6IBsL1Gn/S0dMxI2RbSgnMKJ.4a"));

    }

}
