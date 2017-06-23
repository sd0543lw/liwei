package com.nowcoder.service;

import com.nowcoder.dao.LoginTicketDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.User;
import com.nowcoder.util.ToutiaoUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by liwei on 17/5/26.
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LoginTicketDAO loginTicketDAO;

    //注册
    public User getUser(int id){
        return userDAO.selectById(id);
    }
    //使用"http://127.0.0.1:8080/reg/?username=liwei&password=0320"做接口测试
    public Map<String,Object> register(String username, String password){
        Map<String,Object> map=new HashedMap();
        if (StringUtils.isBlank(username)) {
            map.put("msgname", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msgpsword", "密码不能为空");
            return map;
        }
        if (userDAO.selectByName(username)!=null){
            map.put("msgname","用户名已经被注册");
            return map;

        }
        User user=new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setPassword(ToutiaoUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);
        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    //登陆
    public Map<String,Object> login(String username, String password){
        Map<String,Object> map=new HashedMap();
        if (StringUtils.isBlank(username)) {
            map.put("msgname", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msgpsword", "密码不能为空");
            return map;
        }

        User user=userDAO.selectByName(username);
        if (user==null){
            map.put("msgname","该用户不存在");
            return map;

        }
        if (!ToutiaoUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
            map.put("msgpassword","用户密码不正确");
            return map;
        }

        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);                      //登录成功时会给用户一个ticket（token）
        return map;
    }
    public void Logout(String ticket){
        loginTicketDAO.updateStatus(ticket,1);
    }
    private String addLoginTicket(int userId){
        LoginTicket ticket=new LoginTicket();
        Date date=new Date();
        date.setTime(date.getTime()+1000*3600*24);
        ticket.setUserId(userId);
        ticket.setStatus(0);
        ticket.setExpired(date);   //设置该通行证的生效时间为一天
        ticket.setTicket(UUID.randomUUID().toString().replace("-",""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }
}
