package com.mytaotao.sso.service;

import com.mytaotao.sso.pojo.User;

/**
 * Created by liqiyu on 2016/8/17 0017.
 */
public interface Function {
    void callback(User user);
}
