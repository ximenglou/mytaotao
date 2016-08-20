package com.mytaotao.manage.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mytaotao.manage.pojo.ItemParam;
import com.mytaotao.manage.service.ItemParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liqiyu on 2016/08/13 0:07.
 */
@RequestMapping("item/param")
@Controller
public class ItemParamController {
    private static final Logger LOG = LoggerFactory.getLogger(ItemParamController.class);

    @Autowired
    ItemParamService itemParamService;

    @RequestMapping(value = "{itemCatId}", method = RequestMethod.GET)
    public ResponseEntity<ItemParam> isExist(@PathVariable("itemCatId") Long itemCatId) {
        try {
            if (LOG.isInfoEnabled()) {
                LOG.info("------李奇玉------开始查询此itemParam是否存在itemCatId值=" + itemCatId + "," + "当前类=ItemParamController.isExist()");
            }
            ItemParam itemParam = new ItemParam();
            itemParam.setItemCatId(itemCatId);
            ItemParam itemParam1 = itemParamService.selectOne(itemParam);
            if (itemParam1 != null) {
                return ResponseEntity.ok(itemParam1);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> list(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows) {
        try {
            if (LOG.isInfoEnabled()) {
                LOG.info("------李奇玉------开始分页查询page+rows值=" + page + rows + "," + "当前类=ItemParamController.list()");
            }
            PageHelper.startPage(page, rows);
            List<ItemParam> itemParams = itemParamService.selectAllWithItemCatName();
            if (itemParams != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("total", ((Page) itemParams).getTotal());
                map.put("rows", ((Page) itemParams).getResult());
                return ResponseEntity.ok(map);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "{cid}", method = RequestMethod.POST)
    public ResponseEntity<Void> save(@PathVariable("cid") Long cid, @RequestParam("paramData") String paramData) {
        try {
            if (LOG.isInfoEnabled()) {
                LOG.info("------李奇玉------开始保存itemParam其cid值=" + cid + "," + "当前类=ItemParamController.save()");
            }
            if (cid == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            ItemParam itemParam = new ItemParam();
            itemParam.setItemCatId(cid);
            itemParam.setParamData(paramData);
            Date date = new Date();
            itemParam.setCreated(date);
            itemParam.setUpdated(date);
            boolean flag = itemParamService.save(itemParam);
            if (flag) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    //@RequestMapping(method = RequestMethod.GET, value = "{itemCatId}")
    //public ResponseEntity<ItemParam> get(@PathVariable("itemCatId") Long itemCatId) {
    //    try {
    //        ItemParam itemParam = new ItemParam();
    //        itemParam.setItemCatId(itemCatId);
    //        ItemParam itemParam1 = itemParamService.selectOne(itemParam);
    //        if (itemParam1 != null) {
    //            return ResponseEntity.ok(itemParam1);
    //        }else{
    //            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    //        }
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    //}
}
