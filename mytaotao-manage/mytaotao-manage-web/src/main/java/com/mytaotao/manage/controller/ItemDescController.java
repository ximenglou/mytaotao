package com.mytaotao.manage.controller;

import com.mytaotao.manage.pojo.ItemDesc;
import com.mytaotao.manage.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liqiyu on 2016/08/12 20:01.
 */
@RequestMapping("item/desc")
@Controller
public class ItemDescController {
    @Autowired
    ItemDescService itemDescService;

    @RequestMapping(value = "{path}",method = RequestMethod.GET)
    public ResponseEntity<ItemDesc> itemDesc(@PathVariable("path") Long itemId){
        try {
            ItemDesc itemDesc = itemDescService.selectById(itemId);
            if(itemDesc==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(itemDesc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
