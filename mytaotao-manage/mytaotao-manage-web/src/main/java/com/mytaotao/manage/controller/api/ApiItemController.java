package com.mytaotao.manage.controller.api;

import com.mytaotao.manage.pojo.Item;
import com.mytaotao.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liqiyu on 2016/08/16 1:28.
 */
@RequestMapping("api/item")
@Controller
public class ApiItemController {
    @Autowired
    ItemService itemService;
    @RequestMapping(method = RequestMethod.GET,value = "{itemId}")
    public ResponseEntity<Item> queryItemByItemId(@PathVariable("itemId")Long id){
        try {
            Item item = itemService.selectById(id);
            if(item!=null){
                return ResponseEntity.ok(item);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
