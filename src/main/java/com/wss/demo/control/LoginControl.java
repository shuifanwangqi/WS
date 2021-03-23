package com.wss.demo.control;


import com.sun.org.apache.regexp.internal.RE;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.wss.demo.dto.User;
import com.wss.demo.service.LoginService;
import com.wss.demo.util.Result;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;
@RestController
public class LoginControl {
    @Autowired
    LoginService loginService;
    @RequestMapping(value ="/login",method = RequestMethod.POST)
    public Result login(@RequestBody User user){
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(
                user.getUserName(),
                user.getPassword()
        );
        Result result=new Result();
        try{
            subject.login(usernamePasswordToken);
        }catch(AuthenticationException e){
            e.printStackTrace();
            return result.fail("账号或密码错误");
        }catch (AuthorizationException e){
            e.printStackTrace();
            return result.fail("没有权限");
        }
        Session session=subject.getSession();
        Set<String>  permissions= permissions=new HashSet<>(loginService.findPermissions(user.getUserName()));
        session.setAttribute("permissions",permissions);
        user.setUrls(permissions);
        user.setPassword("null");
        Result r= result.success(user);
        return r;
    }

    @RequestMapping(value="/index",method = RequestMethod.POST)
    public String index(HttpSession httpSession){
        Session session=SecurityUtils.getSubject().getSession();
        System.err.println(session);
        System.err.println(httpSession);
        httpSession.setAttribute("http","httpsession测试");
        Object attribute=session.getAttribute("http");
        System.err.println(attribute);
        return "index";
    }

    @RequestMapping(value="/hh",method = RequestMethod.POST)
    public String hh(HttpSession httpSession){
        Session session=SecurityUtils.getSubject().getSession();
        System.err.println(session);
        System.err.println(httpSession);
        httpSession.setAttribute("http","httpsession测试");
        Object attribute=session.getAttribute("http");
        System.err.println(attribute);
        return "index";
    }

    @RequestMapping("/logout")
    public Result logout(HttpServletResponse response) {
        Subject lvSubject=SecurityUtils.getSubject();
        lvSubject.logout();
        response.setStatus(200);
        Result result=new Result();
        return result.success(null);
        //response.setStatusCode(HttpStatus.FOUND);

    }
}
