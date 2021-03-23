package com.wss.demo.service;

import com.wss.demo.dto.User;
import com.wss.demo.vo.MonitorVo;

import java.util.List;

public interface LoginService {
    /**
     * 根据用户名 返回用户信息 包含用户名、密码和盐
     * @param name
     * @return
     */
    public User findUser(String name);

    /**
     * 根据用户名，返回用户权限信息
     * @param name
     * @return
     */
    public List<String> findPermissions(String name);

}
