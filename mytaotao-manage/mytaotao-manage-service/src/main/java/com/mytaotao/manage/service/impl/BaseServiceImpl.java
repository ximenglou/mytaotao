package com.mytaotao.manage.service.impl;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.mytaotao.manage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liqiyu on 2016/08/11 19:01.
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    protected Mapper<T> mapper;

    @Override
    public boolean save(T t) {
        return mapper.insert(t) == 1;
    }

    @Override
    public boolean saveSelective(T t){
        return mapper.insertSelective(t) == 1;
    }

    @Override
    public boolean deleteById(Serializable id){
        return mapper.deleteByPrimaryKey(id) == 1;
    }

    @Override
    public boolean deleteByIds(Class<T> clazz, String id, List ids){
        Example example = new Example(clazz);
        example.createCriteria().andIn(id,ids);
        return mapper.deleteByExample(example) > 0;
    }

    @Override
    public boolean update(T t){
        return mapper.updateByPrimaryKey(t) == 1;
    }

    @Override
    public boolean updateByPrimaryKeySelective(T t){
        return mapper.updateByPrimaryKeySelective(t) == 1;
    }

    public T selectOne(T t){
        return mapper.selectOne(t);
    }

    @Override
    public T selectById(Serializable id){
        return mapper.selectByPrimaryKey(id);
    }

    public  List<T> selectByWhere(T t){
        return mapper.select(t);
    }

    @Override
    public  List<T> selectAll(){
        return mapper.select(null);
    }

    @Override
    public List<T> selectByExample(Example example){
        return mapper.selectByExample(example);
    }

}
