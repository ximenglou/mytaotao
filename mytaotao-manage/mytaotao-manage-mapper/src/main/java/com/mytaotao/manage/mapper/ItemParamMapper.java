package com.mytaotao.manage.mapper;

import com.github.abel533.mapper.Mapper;
import com.mytaotao.manage.pojo.ItemParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liqiyu on 2016/08/12 23:48.
 */
@Repository
public interface ItemParamMapper extends Mapper<ItemParam> {
    List<ItemParam> selectAllWithItemCatName();
}
