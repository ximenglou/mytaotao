package com.mytaotao.seach.controller;

import com.mytaotao.seach.bean.Item;
import com.mytaotao.seach.bean.SearchResult;
import com.mytaotao.seach.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/**
 * Created by liqiyu on 2016/8/20 0020.
 */
@Controller
public class SearchController {
    public static final int ROWS=30;
    @Autowired
    SearchService searchService;
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam("q") String keyWords,
                               @RequestParam(value = "page", defaultValue = "1") Integer page) {
        ModelAndView mv = new ModelAndView("search");

        SearchResult searchResult = null;
        try {
            keyWords = new String(keyWords.getBytes("ISO-8859-1"),"UTF-8");
            searchResult = this.searchService.searchItem(keyWords, page, ROWS);
        } catch (Exception e) {
            e.printStackTrace();
            searchResult = new SearchResult( 0L,new ArrayList<Item>(0));
        }

        // 搜索关键字
        mv.addObject("query", keyWords);

        // 搜索结果集
        mv.addObject("itemList", searchResult.getList());

        // 当前页数
        mv.addObject("page", page);

        // 总页数
        int total = searchResult.getTotal().intValue();
        int pages = total % ROWS == 0 ? total / ROWS : total / ROWS + 1;
        mv.addObject("pages", pages);

        return mv;
    }

}
