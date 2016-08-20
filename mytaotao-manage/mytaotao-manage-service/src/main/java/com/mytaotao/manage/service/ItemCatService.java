package com.mytaotao.manage.service;

import com.mytaotao.manage.pojo.ItemCat;
import com.mytaotao.manage.pojo.ItemCatResult;

/**
 * Created by liqiyu on 2016/08/10 0:43.
 */
public interface ItemCatService extends BaseService<ItemCat>{
    ItemCatResult findAllToTree();
}
