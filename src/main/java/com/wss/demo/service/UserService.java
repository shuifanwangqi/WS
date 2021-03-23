package com.wss.demo.service;

import com.sun.org.apache.xerces.internal.dom.AbortException;
import com.wss.demo.dto.User;
import com.wss.demo.dto.UserDto;
import com.wss.demo.vo.RoleVo;
import com.wss.demo.vo.UserVo;

import java.util.List;

public interface UserService {
    List<UserVo> getUsers(UserDto userDto);
    List<RoleVo> getRoles();
    Integer getTotal(UserDto userDto);
    boolean AddUser(UserDto user);
    boolean DeleteUser(Integer id);
    boolean BatchDelete(List<Integer> ids);


}
