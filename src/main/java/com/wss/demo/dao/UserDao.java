package com.wss.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import com.wss.demo.dto.User;
import com.wss.demo.dto.UserDto;
import com.wss.demo.vo.RoleVo;
import com.wss.demo.vo.UserVo;

import java.util.List;
@Mapper
public interface UserDao {
    List<UserVo> getUser(UserDto userDto);
    List<RoleVo> getRole();
    Integer  getTotal(UserDto userDto);
  /*  void deleteUser(Integer id);
    void batchDeleteUser(List<Integer> ids);

   */
    Integer addUser(UserDto user);
    Integer UserExit(String name);
    void  deleteUser(Integer userId);
    void  deleteUserRole(Integer userId);
    void addUserRole(UserDto user);
}
