package com.mytaotao.manage.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytaotao.common.service.RedisService;
import com.mytaotao.manage.pojo.ItemCatResult;
import com.mytaotao.manage.service.ItemCatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by liqiyu on 2016/08/14 8:53.
 */
@RequestMapping("web/item/cat")
@Controller
public class ApiItemCatController {

    public static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    ItemCatService itemCatService;
    @Autowired
    RedisService redisService;
private static final Logger LOG = LoggerFactory.getLogger(ApiItemCatController.class);
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> itemcat(@RequestParam(value = "callback", required = false) String callback) {

        String key = "TAOTAO_MANAGE_ITEM_CAT_ALL";
        String value = null;
        try {
            value = redisService.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if(LOG.isInfoEnabled()){
            LOG.info("------李奇玉------当前value值=" + value + "," + "当前位置=ApiItemCatController.itemcat()");
            }
            if (StringUtils.isEmpty(value)) {
                ItemCatResult itemCatResult = itemCatService.findAllToTree();
                if(LOG.isInfoEnabled()){
                LOG.info("------李奇玉------进行了一次全部类目查询="   + "," + "当前类=ApiItemCatController.itemcat()");
                }
                String s = mapper.writeValueAsString(itemCatResult);
                value = StringUtils.isEmpty(callback) ? s : callback + "(" + s + ");";
                try {
                    redisService.set(key, value);
                    redisService.expire(key,60*60*24*30*3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return ResponseEntity.ok(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
