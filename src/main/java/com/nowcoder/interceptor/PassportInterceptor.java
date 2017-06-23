package com.nowcoder.interceptor;

import com.nowcoder.dao.LoginTicketDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.HostHolder;
import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.User;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by liwei on 17/6/10.
 *拦截器：判断该用户是否是登录用户，如果是登录用户才能进行某些操作
 *
 */
@Component
public class PassportInterceptor implements HandlerInterceptor{
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LoginTicketDAO loginTicketDAO;
    @Autowired
    private HostHolder hostHolder;
    /*任何操作之前，先判断是否是登录态，即cookie中的ticket字段是否有值。Controller的方法执行前会被调用*/
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket=null;
        Cookie[] cookies=httpServletRequest.getCookies();
        if (cookies!=null){
            for (Cookie cookie:cookies){
                if (cookie.getName().equals("ticket")){
                    ticket=cookie.getValue();
                    break;
                }

            }
        }
        if (ticket!=null){
            LoginTicket loginTicket=loginTicketDAO.selectByTicket(ticket);
            if (loginTicket==null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus()!=0){
                return false;
            }
            User user=userDAO.selectById(loginTicket.getUserId());
            hostHolder.setUser(user);     //使用threadlocal将当前user传下去，供后面的service判断是哪个user
        }
        return true;
    }

    @Override    //后端逻辑都处理完了，传给前端。即前端和后端交互的地方
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && hostHolder.getUser() != null) {
            modelAndView.addObject("user", hostHolder.getUser());   //在用户请求处理完成之后，将当前user返回给view，方便前端展示当前用户是谁
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
