package com.eamondocker.mysqltest.controller;

import com.alibaba.fastjson.JSONObject;
import com.eamondocker.mysqltest.entity.User;

import com.eamondocker.mysqltest.mapper.UserMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
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
    public String test() {
        return "Hello Eamon!";
    }

    @GetMapping("/getUser")
    public List<User> getUser() {
        List<User> users = userMapper.selectList(null);
        return users;
    }

    @GetMapping("/getViewNum")
    public String getViewNum() throws IOException {

        //获得浏览量
        Long views = redisTemplate.opsForValue().increment("Views");
        //通过api获取ip
        String ip4 = getIp();
        System.out.println(ip4);
        //判断当前IP是否访问过改页
        if (redisTemplate.hasKey("IP") && ip4.equals(redisTemplate.opsForValue().get("IP").toString())) {
            return "当前IP:" + ip4 + " 第: " + views + " 次访问";
        }
        //没有访问过，就把新的IP放到redis里面
        else {
            redisTemplate.delete("IP");
            redisTemplate.delete("Views");
            //将ip存入redis
            redisTemplate.opsForValue().set("IP", ip4);
            return "当前IP:" + ip4 + " 第: " + views + " 次访问";
        }

    }

    //获取ip地址
    @GetMapping("/getIP")
    public String getIp() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("http://pv.sohu.com/cityjson%C2%A0")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        //删除无用字符串
        String str = response.body().string();
        String str1 = str.substring(19);
        String str2 = str1.substring(0, str1.length() - 1);
        //将String转json
        JSONObject jsonObject = JSONObject.parseObject(str2);
        //获取ip地址
        Object ip = jsonObject.get("cip");
        return (String) ip;
    }

}
