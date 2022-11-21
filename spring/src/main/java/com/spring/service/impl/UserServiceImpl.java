package com.spring.service.impl;

import com.spring.entity.User;
import com.spring.mapper.UserMapper;
import com.spring.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    public List<User> getUserList(){
        return userMapper.getUserList();
    };

    public User getByName(String name){ return userMapper.getByName(name);}

    public List<User> getLikeName(String name){return userMapper.getLikeName(name);}

    public int save(User user){
        return userMapper.save(user);
    };

    public int update(User user){
        return userMapper.update(user);
    };

    public int delete(Long id){return  userMapper.delete(id);};
}
