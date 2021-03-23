package com.wss.demo.shiro.realm;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import com.wss.demo.dto.Permission;
import com.wss.demo.dto.Role;
import com.wss.demo.dto.User;
import com.wss.demo.service.LoginService;

import java.util.HashSet;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){

        String name=(String)principals.getPrimaryPrincipal();
        Subject subject= SecurityUtils.getSubject();
        Session session=subject.getSession();
        Set<String>  permissions=(Set<String>)session.getAttribute("permissions");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if(permissions==null) {
            permissions=new HashSet<>(loginService.findPermissions(name));
            session.setAttribute("permissions",permissions);
        }
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken){
        if(authenticationToken.getPrincipal()==null){
            return null;
        }

        String name=authenticationToken.getPrincipal().toString();
        User user=loginService.findUser(name);
        if(user==null){
            return  null;
        }
        else {
            ByteSource salt=ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(user.getUserName(),user.getPassword(),salt,getName());
            return   simpleAuthenticationInfo;
        }
    }

}
