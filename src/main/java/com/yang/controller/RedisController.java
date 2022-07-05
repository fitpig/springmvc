package com.yang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:Yang
 */
@Controller
public class RedisController {
    @Autowired
    private RedisTemplate template;

    @RequestMapping("/redis")
    @ResponseBody
    public String getredis(){
        ValueOperations valueOperations = template.opsForValue();
        Object user = valueOperations.get("user");
        return user.toString();
    }
}
