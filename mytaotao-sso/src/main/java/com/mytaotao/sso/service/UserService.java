package com.mytaotao.sso.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytaotao.common.service.RedisService;
import com.mytaotao.sso.mapper.UserMapper;
import com.mytaotao.sso.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

/**
 * Created by liqiyu on 2016/8/17 0017.
 */
@org.springframework.stereotype.Service
public class UserService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserMapper userMapper;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    RedisService redisService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Boolean check(String param, int type) {

        Boolean flag = false;
        switch (type) {
            //username
            case 1:
                flag = this.checkUsername(param);
                break;
            //phone
            case 2:
                flag = this.checkPhone(param);
                break;
            //email
            case 3:
                flag = this.checkEmail(param);
                break;
            default:
                flag = null;
        }
        return flag;
    }

    private boolean execute(Function fun) {
        User user = new User();
        fun.callback(user);
        return userMapper.selectOne(user) == null;
    }

    private boolean checkUsername(final String param) {
        return this.execute(new Function() {
            @Override
            public void callback(User user) {
                user.setUsername(param);
            }
        });
    }


    private boolean checkPhone(final String param) {
        return this.execute(new Function() {
            @Override
            public void callback(User user) {
                user.setPhone(param);
            }
        });
    }

    private boolean checkEmail(final String param) {
        return this.execute(new Function() {
            @Override
            public void callback(User user) {
                user.setEmail(param);
            }
        });
    }

    public Boolean doRegister(User user) {
        Date date = new Date();
        user.setCreated(date);
        user.setUpdated(date);
        String password = user.getPassword();
        String md5Hex = DigestUtils.md5Hex(password);
        user.setPassword(md5Hex);
        int insert = userMapper.insert(user);
        return insert == 1;
    }

    public String doLogin(User user) throws JsonProcessingException {
        String password = user.getPassword();
        String md5Hex = DigestUtils.md5Hex(password);
        user.setPassword(md5Hex);
        User user1 = new User();
        user1.setUsername(user.getUsername());
        User user2 = userMapper.selectOne(user1);
        if (user2 == null) {
            return null;
        }
        if (!user2.getPassword().equals(user.getPassword())) {
            return null;
        }
        String token = "TOKEN_" + UUID.randomUUID().toString().replace("-", "");
        this.redisService.set(token, MAPPER.writeValueAsString(user2));
        this.redisService.expire(token, 60 * 30);
        return token;
    }


    public String queryToken(String callback, String token) {
        String jsonData = this.redisService.get(token);
        if (StringUtils.isEmpty(jsonData)) {
            return null;
        }
        //刷新登录时间
        this.redisService.expire(token, 60 * 30);
        if (StringUtils.isEmpty(callback)) {
            return jsonData;
        }
        return callback + "(" + jsonData + ")";
    }
}
