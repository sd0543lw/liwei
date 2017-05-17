package com.nowcoder.toutiao.controller;

import com.sun.javafx.iio.gif.GIFDescriptor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.rmi.server.UID;

/**
 * Created by liwei on 17/5/17.
 * 注：（1）controller包内是所有url的入口，做解析
 *    （2）spring中有很多注解，表明这个类是用来做什么的
 */
@Controller
public class IndexController {
//    @RequestMapping("/")     //指定哪个url由这个方法处理
    @RequestMapping(path = {"/","/index"})   //表明"/"和"/index"这两个路径都能访问该方法
    @ResponseBody            //指定返回是一个body
    public String index(){

        return ("Hello NowCoder");
    }
    @RequestMapping(value = "/proflie/{groupId}/{userId}")       //括号中的value和path是一个意思，都可以。或者直接不写
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,   //url路径中的参数用@PathVariable注解
                          @PathVariable("userId") int userId,
                          @RequestParam(value="type",defaultValue="1") int type,     // 问号后面的参数用@RequestParam注解
                          @RequestParam(value="key",defaultValue="noeCoder") String key){
        return String.format("GID{%s}, UID{%d},TYPE{%d},KEY{%s}",groupId,userId,type,key);
    }
    @RequestMapping(path = "/VM")
    public String news(Model model){
        return "news";
    }
}
