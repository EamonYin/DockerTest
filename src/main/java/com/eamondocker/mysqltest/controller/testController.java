package com.eamondocker.mysqltest.controller;

import com.eamondocker.mysqltest.entity.User;

import com.eamondocker.mysqltest.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author:YiMing
 * @create:2021/2/4,17:21
 * @version:1.0
 */
@RestController
public class testController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/hello")
    public String test(){
        return "Hello Eamon!";
    }

    @GetMapping("/getUser")
    public List<User> getUser(){
        List<User> users = userMapper.selectList(null);
        return users;
    }

    


}
