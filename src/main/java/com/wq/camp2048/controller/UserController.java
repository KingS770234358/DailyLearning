package com.wq.camp2048.controller;

import com.wq.camp2048.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getAll")
    public String getAll(){
        // return JSON.toJSONString(userService.getAll());
        return userService.getAll().toString();
    }
}
