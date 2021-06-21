package com.yaobanTech.springcloud.service;

import com.yaobanTech.springcloud.mapper.UserMapper;
import com.yaobanTech.springcloud.pojos.JwtUser;
import com.yaobanTech.springcloud.pojos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value = "myUserDetailsService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    private LinkedHashMap<Object, Object> part;

    public void save(User user) {
        userMapper.save(user);
    }

    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在!");
        }
        //user.setRole(userMapper.getHrRolesById(user.getId()));
        return new JwtUser(user);
    }


}
