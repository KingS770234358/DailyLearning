package com.wq.camp2048.service;

import com.wq.camp2048.mapper.UserMapper;
import com.wq.camp2048.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAll() {
        List<User> userList = userMapper.getAllUser();
        for (User user : userList) {
            System.out.println(user);
        }
        return userList;
    }
}
