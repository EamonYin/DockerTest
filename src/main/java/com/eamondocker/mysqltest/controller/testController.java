package com.eamondocker.mysqltest.controller;

import com.eamondocker.mysqltest.entity.User;

import com.eamondocker.mysqltest.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @author:YiMing
 * @create:2021/2/4,17:21
 * @version:1.0
 */
@RestController
public class testController implements Serializable {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/hello")
    public String test(){
        return "Hello Eamon!";
    }

    @GetMapping("/getUser")
    public List<User> getUser(){
        List<User> users = userMapper.selectList(null);
        return users;
    }

    @GetMapping("/getViewNum")
    public String getViewNum(){

        //获得浏览量
        Long views = redisTemplate.opsForValue().increment("Views");
        //获取当前IP
        InetAddress ip4 = null;
        try {
            ip4 = InetAddress.getByName(Inet4Address.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //将ip存入redis
        redisTemplate.opsForValue().set("IP",ip4);
        //从redis中读取ip
        Object ip = redisTemplate.opsForValue().get("IP");

        return "当前第: "+views+" 次访问"+" 当前IP:"+ip;
    }
}
