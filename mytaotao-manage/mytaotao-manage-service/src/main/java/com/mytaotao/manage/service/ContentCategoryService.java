package com.mytaotao.manage.service;

import com.mytaotao.manage.pojo.ContentCategory;

/**
 * Created by liqiyu on 2016/08/10 0:43.
 */
public interface ContentCategoryService extends BaseService<ContentCategory>{
    ContentCategory saveAndUpdate(Long parentId, String name);

    boolean deleteAllById(Long parentId, Long id);
}
