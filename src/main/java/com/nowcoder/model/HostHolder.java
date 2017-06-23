package com.nowcoder.model;

import org.springframework.stereotype.Component;

/**
 * Created by liwei on 17/6/10.
 * 存放当前登录中的用户，使用threadlocal使得各user在自己的泳道中，不受干扰
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users=new ThreadLocal<>();
    public User getUser(){return users.get();}
    public void setUser(User user){users.set(user);}
    public void clear(){
        users.remove();
    }
}
