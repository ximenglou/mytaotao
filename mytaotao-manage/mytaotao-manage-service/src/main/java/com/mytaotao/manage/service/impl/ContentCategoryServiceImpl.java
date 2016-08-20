package com.mytaotao.manage.service.impl;

import com.mytaotao.manage.pojo.ContentCategory;
import com.mytaotao.manage.service.ContentCategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liqiyu on 2016/08/14 22:20.
 */
@Service("contentCategoryService")
public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategory> implements ContentCategoryService {
    @Override
    public ContentCategory saveAndUpdate(Long parentId, String name) {
        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setParentId(parentId);
        contentCategory.setIsParent(false);
        contentCategory.setName(name);
        contentCategory.setSortOrder(1);
        contentCategory.setStatus(1);
        Date date = new Date();
        contentCategory.setCreated(date);
        contentCategory.setUpdated(date);
        boolean count = super.save(contentCategory);

        ContentCategory parent = super.selectById(parentId);
        if(parent==null){
            count=false;
        }else{
            if(!parent.getIsParent()){
                parent.setId(parentId);
                parent.setIsParent(true);
                count = super.updateByPrimaryKeySelective(parent);
            }
        }
        if(count) {
            return contentCategory;
        }else{
            return null;
        }
    }

    public boolean deleteAllById(Long parentId, Long id) {
        List<Long> list = new ArrayList<>();
        list.add(id);
        this.deleteChild(list,id);
        boolean flag = super.deleteByIds(ContentCategory.class, "id", list);
        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setParentId(parentId);
        List<ContentCategory> list1 = super.selectByWhere(contentCategory);
        if(list1==null||list1.isEmpty()){
            ContentCategory contentCategory1 = new ContentCategory();
            contentCategory1.setId(parentId);
            contentCategory1.setIsParent(false);
            super.updateByPrimaryKeySelective(contentCategory1);
        }


        return flag;
    }

    private void deleteChild(List<Long> list, Long id) {
        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setParentId(id);
        List<ContentCategory> contentCategories = super.selectByWhere(contentCategory);
        for (ContentCategory c : contentCategories) {
            list.add(c.getId());
            if (c.getIsParent()) {
                deleteChild(list,c.getId());
            }
        }
    }
}
