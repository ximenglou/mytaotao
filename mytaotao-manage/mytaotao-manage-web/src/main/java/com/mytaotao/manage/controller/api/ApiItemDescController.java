package com.mytaotao.manage.controller.api;

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
 * Created by liqiyu on 2016/08/16 2:24.
 */
@RequestMapping("api/item/desc")
@Controller
public class ApiItemDescController {
    @Autowired
    ItemDescService itemDescService;
    @RequestMapping(method = RequestMethod.GET,value = "{itemId}")
    public ResponseEntity<ItemDesc> queryItemDescByItemId(@PathVariable("itemId") Long itemId){
        try {
            ItemDesc itemDesc = itemDescService.selectById(itemId);
            if (itemDesc != null) {
                return ResponseEntity.ok(itemDesc);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
