package com.nowcoder.controller;

import com.nowcoder.model.News;
import com.nowcoder.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwei on 17/5/26.
 */
@Controller
public class HomeController {
 @Autowired
 NewsService newsService;
    @RequestMapping(value = {"/","/index"},method = {RequestMethod.GET,RequestMethod.POST})
    public String index(Model model){
        List<News> newsList= newsService.getLatestNews(0,0,10);

        return "home";
    }
}
