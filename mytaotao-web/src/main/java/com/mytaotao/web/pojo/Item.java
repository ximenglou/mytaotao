package com.mytaotao.web.pojo;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by liqiyu on 2016/08/16 0:06.
 */
public class Item extends com.mytaotao.manage.pojo.Item {
public String[] getImages(){
    return StringUtils.split(super.getImage(),",");
}
}
