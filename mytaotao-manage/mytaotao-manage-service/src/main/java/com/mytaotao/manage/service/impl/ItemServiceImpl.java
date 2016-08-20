package com.mytaotao.manage.service.impl;

import com.mytaotao.manage.pojo.Item;
import com.mytaotao.manage.pojo.ItemDesc;
import com.mytaotao.manage.pojo.ItemParamItem;
import com.mytaotao.manage.service.ItemDescService;
import com.mytaotao.manage.service.ItemParamItemService;
import com.mytaotao.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by liqiyu on 2016/08/11 21:33.
 */
@Service("itemService")
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

    @Autowired
    ItemDescService itemDescService;
    @Autowired
    ItemParamItemService itemParamItemService;

    @Override
    public Boolean save(Item item, String desc,String itemParams) {

        boolean flag=false;
        try {
            Date date = new Date();
            item.setId(null);
            item.setStatus(1);
            item.setUpdated(date);
            item.setCreated(date);
            //保存商品信息(除描述)
            flag=mapper.insert(item)==1;

            if (flag) {
                //保存商品描述
                ItemDesc itemDesc = new ItemDesc();
                itemDesc.setCreated(date);
                itemDesc.setUpdated(date);
                itemDesc.setItemDesc(desc);
                itemDesc.setItemId(item.getId());
                flag = itemDescService.save(itemDesc);
                //保存商品参数
                ItemParamItem itemParamItem = new ItemParamItem();
                itemParamItem.setCreated(date);
                itemParamItem.setUpdated(date);
                itemParamItem.setItemId(item.getId());
                itemParamItem.setParamData(itemParams);
                flag = itemParamItemService.save(itemParamItem);
                return flag;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean edit(Item item, String desc,String itemParams,Long itemParamId) {
        boolean flag=false;
        try {
            Date date = new Date();
            item.setUpdated(date);
            flag = mapper.updateByPrimaryKeySelective(item)==1;

            if (flag) {
                //保存商品描述
                ItemDesc itemDesc = new ItemDesc();
                itemDesc.setUpdated(date);
                itemDesc.setItemDesc(desc);
                itemDesc.setItemId(item.getId());
                flag = itemDescService.updateByPrimaryKeySelective(itemDesc);
                //保存商品参数
                ItemParamItem itemParamItem = new ItemParamItem();
                itemParamItem.setUpdated(date);
                itemParamItem.setItemId(item.getId());
                itemParamItem.setParamData(itemParams);
                itemParamItem.setId(itemParamId);
                flag = itemParamItemService.updateByPrimaryKeySelective(itemParamItem);
                return flag;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
