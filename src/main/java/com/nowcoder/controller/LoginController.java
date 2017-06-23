package com.nowcoder.controller;

import com.nowcoder.service.UserService;
import com.nowcoder.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by liwei on 17/6/5.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;
    @RequestMapping(path={"/reg/"},method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String reg(Model model, @RequestParam("username") String username,
                    @RequestParam("password") String password,
                    @RequestParam(value="rember",defaultValue = "0") int rember,  //即前端页面"记住我"单选框
                    HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rember>0)
                cookie.setMaxAge(1000*3600*5);   //设置cookie有效时间是五天
                response.addCookie(cookie);
                return ToutiaoUtil.getJSONString(0, "注册成功");
            }
            else
                return ToutiaoUtil.getJSONString(1,map);
        } catch (Exception e) {
            logger.error("注册异常：" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "注册异常");
        }


    }
    @RequestMapping(path ={"/login/"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "rember",defaultValue ="0") int rember){
        Map<String ,Object> map=userService.login(username,password);
        if (map.containsKey("ticket")){
            Cookie cookie=new Cookie("ticket",map.get("ticket").toString());
            cookie.setPath("/");
            if (rember>0)
                cookie.setMaxAge(1000*3600*5);
            return ToutiaoUtil.getJSONString(0,"登录成功");
        }
        else
            return ToutiaoUtil.getJSONString(1,"登录失败");
    }
    @RequestMapping(path ={"/logout/"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String logout(@CookieValue("ticket") String ticket){  //通过注解直接获取cookie的ticket值
        userService.Logout(ticket);
        return "redirect:/";
    }


}
