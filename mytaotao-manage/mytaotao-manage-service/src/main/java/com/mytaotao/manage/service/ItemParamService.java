package com.mytaotao.manage.service;

import com.mytaotao.manage.pojo.ItemParam;

import java.util.List;

/**
 * Created by liqiyu on 2016/08/10 0:43.
 */
public interface ItemParamService extends BaseService<ItemParam>{
    List<ItemParam> selectAllWithItemCatName();
}
