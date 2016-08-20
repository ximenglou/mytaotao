package com.mytaotao.manage.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mytaotao.manage.pojo.Item;
import com.mytaotao.manage.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liqiyu on 2016/08/11 20:48.
 */
@Controller
@RequestMapping("item")
public class ItemController {

    @Autowired
    ItemService itemService;

    private static final Logger LOG = LoggerFactory.getLogger(ItemController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getItem(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "rows", defaultValue = "30") Integer rows) {
        if (LOG.isInfoEnabled()) {
            LOG.info("------李奇玉------准备处理的page+rows值=" + page + rows + "," + "当前类=ItemController.getItem()");
        }
        try {
            PageHelper.startPage(page, rows);
            List<Item> items = itemService.selectAll();
            Map<String, Object> map = new HashMap<>();
            map.put("total", ((Page) items).getTotal());
            map.put("rows", ((Page) items).getResult());
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            if (LOG.isInfoEnabled()) {
                LOG.info("------李奇玉------分页异常,异常信息e的值=" + e + "," + "当前类=ItemController.getItem()");
            }
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> addItem(Item item, @RequestParam("desc") String desc,String itemParams) {
        try {
            if (item.getTitle().isEmpty() || item.getCid() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            Boolean flag = itemService.save(item, desc,itemParams);
            if (flag) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> putItem(Item item, @RequestParam("desc") String desc,@RequestParam("itemParams") String itemParams,@RequestParam("itemParamId")Long itemParamId) {
        try {
            if (item.getTitle().isEmpty() || item.getCid() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            Boolean flag = itemService.edit(item, desc,itemParams,itemParamId);
            if (flag) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}