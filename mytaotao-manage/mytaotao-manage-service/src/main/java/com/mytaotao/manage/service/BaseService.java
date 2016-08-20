package com.mytaotao.manage.service;

import com.github.abel533.entity.Example;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liqiyu on 2016/08/11 20:15.
 */
public interface BaseService<T> {
    boolean save(T t);

    boolean saveSelective(T t);

    boolean deleteById(Serializable id);

    boolean deleteByIds(Class<T> clazz, String id, List ids);

    boolean  update(T t);

    boolean updateByPrimaryKeySelective(T t);

    T selectById(Serializable id);

    List<T> selectByWhere(T t);

    List<T> selectAll();

    T selectOne(T t);

    List<T> selectByExample(Example example);
}
