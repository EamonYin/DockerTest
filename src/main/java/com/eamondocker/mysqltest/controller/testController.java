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

        String ipAddr = ip4.toString();

        //判断当前IP是否访问过改页
        if(redisTemplate.hasKey("IP")&&ipAddr.equals(redisTemplate.opsForValue().get("IP").toString())){
            return "当前IP:"+ipAddr+" 第: "+views+" 次访问";
        }
        //没有访问过，就把新的IP放到redis里面
        else {
            redisTemplate.delete("IP");
            redisTemplate.delete("Views");
            //将ip存入redis
            redisTemplate.opsForValue().set("IP",ip4);
            return "当前IP:"+ipAddr+" 第: "+views+" 次访问";
        }

    }
}
