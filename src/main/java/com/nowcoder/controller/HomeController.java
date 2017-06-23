package com.nowcoder.controller;

import com.nowcoder.model.News;
import com.nowcoder.model.ViewObject;
import com.nowcoder.service.NewsService;
import com.nowcoder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwei on 17/5/26.
 */
@Controller
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    NewsService newsService;

    @RequestMapping(value = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String index(Model model) {
//        List<News> newsList= newsService.getLatestNews(0,0,10);
//        List<ViewObject> vos=new ArrayList<>();
//        for (News news:newsList){
//            ViewObject vo=new ViewObject();
//            vo.set("news",news);
//            vo.set("user", userService.getUser(news.getUserId()));
//            vos.add(vo);
//
//        }
//        model.addAttribute("vos",vos);
//        return "home";
        return "Hello ,this is index";
    }
}
