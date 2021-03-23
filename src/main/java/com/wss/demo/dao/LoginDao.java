package com.wss.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import com.wss.demo.dto.User;

import java.util.List;
import java.util.Set;

@Mapper
public interface LoginDao {
    User getUser(String name);
    List<String> getPermissions(String name);
}
