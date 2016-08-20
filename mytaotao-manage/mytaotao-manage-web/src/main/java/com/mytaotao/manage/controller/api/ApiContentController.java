package com.mytaotao.manage.controller.api;

import com.github.abel533.entity.Example;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mytaotao.manage.pojo.Content;
import com.mytaotao.manage.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liqiyu on 2016/08/14 22:09.
 */
@RequestMapping("api/content")
@Controller
public class ApiContentController {
    @Autowired
    ContentService contentService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> queryListById(@RequestParam("categoryId")Long categoryId,@RequestParam(defaultValue = "1",value="page")Integer page,@RequestParam(defaultValue = "10",value = "rows")Integer rows){
        PageHelper.startPage(page,rows);
        Example example = new Example(Content.class);
        example.setOrderByClause("updated");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("categoryId",categoryId);
        List<Content> contents = contentService.selectByExample(example);
        if(contents!=null){
            Map<String,Object> map = new HashMap<>();
            map.put("total",((Page<Content>)contents).getTotal());
            map.put("rows",((Page<Content>)contents).getResult());
            return ResponseEntity.ok(map);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
