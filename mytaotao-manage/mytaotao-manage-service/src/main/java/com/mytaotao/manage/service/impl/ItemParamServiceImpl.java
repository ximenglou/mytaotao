package com.mytaotao.manage.service.impl;

import com.mytaotao.manage.mapper.ItemParamMapper;
import com.mytaotao.manage.pojo.ItemParam;
import com.mytaotao.manage.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liqiyu on 2016/08/10 0:44.
 */
@Service("itemParamService")
public class ItemParamServiceImpl extends BaseServiceImpl<ItemParam> implements ItemParamService {

    @Autowired
    ItemParamMapper itemParamMapper;
    @Override
    public List<ItemParam> selectAllWithItemCatName() {
        return itemParamMapper.selectAllWithItemCatName();
    }
}
