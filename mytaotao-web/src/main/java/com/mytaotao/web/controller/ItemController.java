package com.mytaotao.web.controller;

import com.mytaotao.manage.pojo.ItemDesc;
import com.mytaotao.web.pojo.Item;
import com.mytaotao.web.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liqiyu on 2016/08/15 23:39.
 */
@Controller
@RequestMapping("item")
public class ItemController {
    @Autowired
    ItemService itemService;
    private static final Logger LOG = LoggerFactory.getLogger(ItemController.class);
    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public ModelAndView queryItemById(@PathVariable("itemId")Long itemId){
        ModelAndView modelAndView = new ModelAndView("item");
        Item item = itemService.queryItemById(itemId);
        //TODO 商品未找到
        if(LOG.isInfoEnabled()){
        LOG.info("------李奇玉------item值=" + item + "," + "当前位置=ItemController.queryItemById()");
        }
        modelAndView.addObject("item",item);
        ItemDesc itemDesc = itemService.queryItemDescById(itemId);
        modelAndView.addObject("itemDesc",itemDesc);
        return modelAndView;
    }
}
