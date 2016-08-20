package com.mytaotao.manage.service;

import com.mytaotao.manage.pojo.Item;

/**
 * Created by liqiyu on 2016/08/11 20:55.
 */
public interface ItemService extends BaseService<Item>{
    Boolean save(Item item, String desc,String itemParams);

    Boolean edit(Item item, String desc,String itemParams,Long itemParamId);
}
