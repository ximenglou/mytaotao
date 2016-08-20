package com.mytaotao.web.controller;

import com.mytaotao.web.pojo.Item;
import com.mytaotao.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liqiyu on 2016/8/20 0020.
 */
@RequestMapping("cart")
@Controller
public class CartController {
    @Autowired
    ItemService itemService;
    @RequestMapping(value="add/{itemId}",method = RequestMethod.GET)
    public ModelAndView toOrder(@PathVariable("itemId")Long itemId){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order");
        Item item = itemService.queryItemById(itemId);
        modelAndView.addObject(item);
        return modelAndView;
    }
}
