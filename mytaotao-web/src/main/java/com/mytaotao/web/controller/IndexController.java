package com.mytaotao.web.controller;

import com.mytaotao.web.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liqiyu on 2016/08/14 0:31.
 */
@RequestMapping("index")
@Controller
public class IndexController {
    @Autowired
    IndexService indexService;
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("index");
        String queryAD1 = indexService.queryAD1();
        mv.addObject("ad1",queryAD1);

        return mv;
    }
}
