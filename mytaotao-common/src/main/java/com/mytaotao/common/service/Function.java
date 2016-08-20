package com.mytaotao.common.service;

/**
 * Created by liqiyu on 2016/08/15 20:10.
 */
public interface Function<T,E> {
    T callback(E e);
}
