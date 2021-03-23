package com.wss.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.wss.demo.dao.UserDao;
import com.wss.demo.dto.User;
import com.wss.demo.dto.UserDto;
import com.wss.demo.service.UserService;
import com.wss.demo.util.Decrypt;
import com.wss.demo.vo.RoleVo;
import com.wss.demo.vo.UserVo;

import java.util.List;
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public List<UserVo> getUsers(UserDto userDto) {
         return  userDao.getUser(userDto);
    }

    @Override
    public Integer getTotal(UserDto userDto) {
        return userDao.getTotal(userDto);
    }

    @Override
    public boolean DeleteUser(Integer id) {
        try {
            userDao.deleteUser(id);
            userDao.deleteUserRole(id);
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean BatchDelete(List<Integer> ids) {
        return false;
    }

    @Override
    public boolean AddUser(UserDto user) {
        if(userDao.UserExit(user.getUserName())!=null){
            return false;
        }
        try{
            user.setSalt(user.getUserName());
            String password=Decrypt.encrypt(user.getUserName(),user.getPassword());
            user.setPassword(password);
            userDao.addUser(user);
            userDao.addUserRole(user);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public List<RoleVo> getRoles() {
        return userDao.getRole();
    }
}
