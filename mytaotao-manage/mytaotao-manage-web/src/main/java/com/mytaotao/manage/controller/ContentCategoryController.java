package com.mytaotao.manage.controller;

import com.mytaotao.manage.pojo.ContentCategory;
import com.mytaotao.manage.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by liqiyu on 2016/08/14 22:09.
 */
@RequestMapping("content/category")
@Controller
public class ContentCategoryController {
    @Autowired
    ContentCategoryService contentCategoryService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContentCategory>> queryListByParentId(@RequestParam(defaultValue = "0", value = "id") Long parentId) {
        try {
            ContentCategory contentCategory = new ContentCategory();
            contentCategory.setParentId(parentId);
            List<ContentCategory> contentCategories = contentCategoryService.selectByWhere(contentCategory);
            if (contentCategories != null && contentCategories.size() > 0) {
                return ResponseEntity.ok(contentCategories);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ContentCategory> add(@RequestParam("parentId") Long parentId, @RequestParam("name") String name) {
        try {
            ContentCategory contentCategory = contentCategoryService.saveAndUpdate(parentId, name);
            if(contentCategory!=null){
                return ResponseEntity.ok(contentCategory);
            }else{
                return ResponseEntity.status(HttpStatus.CREATED).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> editName(@RequestParam("id")Long id, @RequestParam("name")String name){
        try {
            ContentCategory contentCategory = new ContentCategory();
            contentCategory.setId(id);
            contentCategory.setName(name);
            boolean flag = contentCategoryService.updateByPrimaryKeySelective(contentCategory);
            if(flag){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteById(@RequestParam("parentId")Long parentId,@RequestParam("id")Long id){
        try {
            boolean flag = contentCategoryService.deleteAllById(parentId,id);
            if(flag){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
