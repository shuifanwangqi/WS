package com.wss.demo.control;

import com.wss.demo.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wss.demo.dto.UserDto;
import com.wss.demo.service.UserService;
import com.wss.demo.util.Result;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserControl {
    @Autowired
    UserService userService;
    @RequestMapping("/get")
    public Result getUser(@RequestBody UserDto userDto){
        HashMap<String,Object> resultMap=new HashMap<>();
        resultMap.put("pageSize",userDto.getPageSize());
        resultMap.put("pageIndex",userDto.getPageIndex());
        userDto.setPageIndex((userDto.getPageIndex()-1)*userDto.getPageSize());

        List<UserVo> userVos=userService.getUsers(userDto);
        resultMap.put("users",userVos);
        int total =userService.getTotal(userDto);
        resultMap.put("total",total) ;
        Result result=new Result();
        return result.success(resultMap);
    }
    @RequestMapping("/getRole")
    public  Result getRole(){
        Result result=new Result();
        return result.success(userService.getRoles());
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result addRole(@RequestBody UserDto user){
        Result result=new Result();
        if(userService.AddUser(user)){
            return result.success(null);
        }else {
            return result.fail("插入失败（账号也许已存在）");
        }
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public  Result deleteRole(@RequestParam Integer userId){
        Result result=new Result();
        if(userService.DeleteUser(userId)){
            return result.success(null);
        }else {
            return result.fail("删除失败");
        }
    }
}
