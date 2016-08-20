package com.mytaotao.manage.controller;

import com.mytaotao.manage.pojo.ItemParamItem;
import com.mytaotao.manage.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liqiyu on 2016/08/13 0:07.
 */
@RequestMapping("item/param/item")
@Controller
public class ItemParamItemController {

    @Autowired
    ItemParamItemService itemParamItemService;
    @RequestMapping(method = RequestMethod.GET, value = "{itemId}")
    public ResponseEntity<ItemParamItem> get(@PathVariable("itemId") Long itemId) {
        try {
            ItemParamItem itemParamItem = new ItemParamItem();
            itemParamItem.setItemId(itemId);
            ItemParamItem itemParamItem1 = itemParamItemService.selectOne(itemParamItem);
            if (itemParamItem1 != null) {
                return ResponseEntity.ok(itemParamItem1);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
