package com.wss.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import com.wss.demo.dao.LoginDao;
import com.wss.demo.dto.User;
import com.wss.demo.service.LoginService;

import java.util.List;
import java.util.Set;

@Component
public class LoginServiceImpl implements LoginService {
    @Autowired
    LoginDao loginDao;
    @Override
    public User findUser(String name) {
        User user=loginDao.getUser(name);
        return user;
    }

    @Override
    public List<String> findPermissions(String name) {
        List<String> set=loginDao.getPermissions(name);
        return set;
    }
}
