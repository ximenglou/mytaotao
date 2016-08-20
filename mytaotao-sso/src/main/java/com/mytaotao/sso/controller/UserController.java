package com.mytaotao.sso.controller;

import com.mytaotao.sso.pojo.User;
import com.mytaotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liqiyu on 2016/8/17 0017.
 */
@RequestMapping("user")
@org.springframework.stereotype.Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String toRegister() {
        return "register";
    }

    @RequestMapping(value = "{param}/{type}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> check(@PathVariable("param") String param, @PathVariable("type") int type) {
        Boolean flag = null;
        try {
            flag = userService.check(param, type);
            if (flag == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(!flag);
    }

    @RequestMapping(value = "doRegister", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> doRegiter(@Valid User user, BindingResult result) {
        Map<String, String> map = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for (ObjectError objectError : result.getAllErrors()) {
                errorList.add(objectError.getDefaultMessage());
            }
            String errors = org.apache.commons.lang3.StringUtils.join(errorList, "|");
            map.put("data", "出错啦!"+errors);
            return map;
        }
        try {
            Boolean flag = userService.doRegister(user);
            if (flag) {
                map.put("status", "200");
                map.put("data", "恭喜!");
            } else {
                map.put("data", "出错啦!");
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("data", "出错啦!");
        return map;
    }
/*login start*/

    @RequestMapping(method = RequestMethod.GET, value = "login")
    public String login() {
        return "login";
    }

    /*login end*/
    /*doLogin start*/
    @RequestMapping(value = "doLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doLogin(User user, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        String token = null;
        try {
            token = userService.doLogin(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (StringUtils.isEmpty(token)) {
            return map;
        }
        map.put("success", "http://www.mytaotao.com");
        map.put("status", 200);
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(60 * 30);
        cookie.setDomain("mytaotao.com");
        cookie.setPath("/");
        response.addCookie(cookie);
        return map;
    }

/*doLogin end*/

    /*queryToken start*/
    @RequestMapping(value = "query/{token}", method = RequestMethod.GET)
    @ResponseBody
    public String queryToken(@RequestParam(value="callback",required = false) String callback, @PathVariable("token") String token) {
        String jsonData = null;
        try {
            jsonData = userService.queryToken(callback, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonData;
    }

/*queryToken end*/

}
