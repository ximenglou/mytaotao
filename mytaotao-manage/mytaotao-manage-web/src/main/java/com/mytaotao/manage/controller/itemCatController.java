package com.mytaotao.manage.controller;

import com.mytaotao.manage.pojo.ItemCat;
import com.mytaotao.manage.service.ItemCatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liqiyu on 2016/08/10 0:28.
 */
@RequestMapping("/item/cat")
@Controller
public class itemCatController {
    private static final Logger LOG = LoggerFactory.getLogger(itemCatController.class);
    @Autowired
    ItemCatService itemCatService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ItemCat>> queryItemCatListByParentId(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        try {
            ItemCat itemCat = new ItemCat();
            itemCat.setParentId(parentId);
            List<ItemCat> list = itemCatService.selectByWhere(itemCat);
            if (list == null || list.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping("name")
    @ResponseBody
    public String itemCat(@RequestParam("cid") Long id) {
        if (LOG.isInfoEnabled()) {
            LOG.info("------李奇玉------要查询的类目的cid值=" + id + "," + "当前类=ItemController.itemCat()");
        }
        ItemCat itemCat = itemCatService.selectById(id);
        return itemCat.getName();
    }
}
