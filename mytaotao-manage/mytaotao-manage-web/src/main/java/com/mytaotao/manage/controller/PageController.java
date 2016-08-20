package com.mytaotao.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liqiyu on 2016/08/09 23:41.
 */
@Controller
@RequestMapping("page")
public class PageController {

    @RequestMapping(value = "{pageName}",method = RequestMethod.GET)
    public  String toPage(@PathVariable(value = "pageName") String pageName){
        return pageName;
    }
}
