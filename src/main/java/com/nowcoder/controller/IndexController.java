package com.nowcoder.controller;


import com.nowcoder.aspect.LogAspect;
import com.nowcoder.service.ToutiaoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * Created by liwei on 17/5/17.
 * 注：（1）controller包内是所有url的入口，做解析
 *    （2）spring中有很多注解，表明这个类是用来做什么的
 */
@Controller
public class IndexController {
    private static final Logger logger= LoggerFactory.getLogger(LogAspect.class);

    @Autowired                 //对象注入(该类前有一个@Service标志)
    private ToutiaoService toutiaoService;
//    @RequestMapping("/")     //指定哪个url由这个方法处理
//    @RequestMapping(path = {"/","/index"})   //表明"/"和"/index"这两个路径都能访问该方法
//    @ResponseBody            //指定返回是一个body
//    public String index(HttpSession session){
//        logger.info("visit index");
//        return ("Hello NowCoder  "+session.getAttribute("msg")+toutiaoService.say());
//    }
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

    @RequestMapping(value = "/request")
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse responce,
                          HttpSession session){
        StringBuilder str=new StringBuilder();
        Enumeration<String> headerNames=request.getHeaderNames();    //请求报头中各字段的名字
        while(headerNames.hasMoreElements()){
            String name=headerNames.nextElement();
            str.append(name+":"+request.getHeader(name)+"<br>");
        }
        for(Cookie cookie :request.getCookies()){
            str.append(cookie.getValue()+"<br>");
        }
        str.append(request.getMethod()+"<br>");
        return str.toString();
    }
    @RequestMapping(value = "/response")
    @ResponseBody
    public String response(@CookieValue(value = "nowcoderid", defaultValue = "a") String nowcoderid,
                           @RequestParam(value = "key" ,defaultValue = "key") String key,
                           @RequestParam(value = "value",defaultValue = "value") String value,
                            HttpServletResponse response){
        response.addCookie(new Cookie(key,value));
        response.addHeader(key,value);
        return "noecoderidformcookie:"+ nowcoderid;

    }

    @RequestMapping(value = "/redirect/{code}")     //301为永久转移，302为临时性的转移
   public RedirectView redirect(@PathVariable(value = "code") int code,
                                HttpSession session){
        RedirectView red=new RedirectView("/",true);
        if (code==301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
            session.setAttribute("msg","context");
        }
        return red;
    }
    @ExceptionHandler           //自己对异常的处理
    @ResponseBody
    public String error(Exception e){

       return e.getMessage();
    }

}
