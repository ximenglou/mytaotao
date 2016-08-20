package com.mytaotao.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytaotao.common.service.RedisService;
import com.mytaotao.manage.pojo.ItemDesc;
import com.mytaotao.web.pojo.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by liqiyu on 2016/08/16 0:10.
 */
@Service
public class ItemService {
    @Autowired
    private ApiService apiService;

    @Autowired
    private RedisService redisService;

    private String ITEM_MYTAOTAO_MANAGE = "ITEM_MYTAOTAO_MANAGE";
    public static final ObjectMapper mapper = new ObjectMapper();

    private static final Logger LOG = LoggerFactory.getLogger(ItemService.class);

    public Item queryItemById(Long itemId) {
        try {
            String jsonData = null;
            try {
                jsonData = redisService.get(ITEM_MYTAOTAO_MANAGE + "_" + itemId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (StringUtils.isEmpty(jsonData)) {
                jsonData = apiService.doget("http://manage.mytaotao.com/rest/api/item/" + itemId);
                if (StringUtils.isEmpty(jsonData)) {
                    return null;
                }
                try {
                    redisService.set(ITEM_MYTAOTAO_MANAGE + "_" + itemId, jsonData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return mapper.readValue(jsonData, Item.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ItemDesc queryItemDescById(Long itemId) {
        try {
            String jsonData = apiService.doget("http://manage.mytaotao.com/rest/api/item/desc/" + itemId);
            return mapper.readValue(jsonData, ItemDesc.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
