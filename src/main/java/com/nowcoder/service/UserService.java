package com.nowcoder.service;

import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liwei on 17/5/26.
 */
public class UserService {
    @Autowired
    private UserDAO userDAO;
    public User getUser(int id){
        return userDAO.selectById(id);
    }
}
