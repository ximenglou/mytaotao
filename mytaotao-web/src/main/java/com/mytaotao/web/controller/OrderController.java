package com.mytaotao.web.controller;

import com.mytaotao.web.intercoper.MytaotaoInterceptor;
import com.mytaotao.web.pojo.Order;
import com.mytaotao.web.pojo.User;
import com.mytaotao.web.service.OrderService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liqiyu on 2016/8/20 0020.
 */


@Controller
@RequestMapping("order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @RequestMapping(value="submit",method = RequestMethod.POST)
    @ResponseBody
    public Map<Object,Object> submit(Order order){
        Map<Object,Object> map = new HashMap<>();
        try {
            User user = MytaotaoInterceptor.threadLocal.get();
            order.setUserId(user.getId());
            order.setBuyerNick(user.getUsername());
            String data = orderService.submit(order);
            if(!StringUtils.isEmpty(data)){
                map.put("status",200);
                map.put("data",data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value = "success",method = RequestMethod.GET)
    public ModelAndView success(@RequestParam("id")Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        Order order = null;
        try {
            order = orderService.queryOrderById(id);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO 跳转错误页面
        }
        modelAndView.addObject("date",new DateTime().plusDays(2).toString("MM月dd日"));
        modelAndView.addObject("order",order);
        return modelAndView;
    }
}
