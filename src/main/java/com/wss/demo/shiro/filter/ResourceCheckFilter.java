package com.wss.demo.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResourceCheckFilter extends FormAuthenticationFilter {
    private String errorUrl;
    private static final Logger logger = LoggerFactory.getLogger(ResourceCheckFilter.class);

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    /**
     * 表示是否允许访问 ，如果允许访问返回true，否则false；
     * @param servletRequest
     * @param servletResponse
     * @param o 表示写在拦截器中括号里面的字符串 mappedValue 就是 [urls] 配置中拦截器参数部分
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) {

            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            //对所有请求进行跨域配置处理
            setHeader(httpServletRequest,httpServletResponse);
            //如果为options请求，直接放行

            if(httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
                httpServletResponse.setStatus(HttpStatus.OK.value());
                return true;
            }

//        如果方法url为login，则放行
            if(httpServletRequest.getServletPath().equals("/login/login")){
                return true;
            }
            String str=httpServletRequest.getServletPath();
            if (httpServletRequest.getServletPath().equals("/logout")){
                return true;
            }
        Subject subject = getSubject(servletRequest,servletResponse);
        String url = getPathWithinApplication(servletRequest);
        logger.debug("当前用户正在访问的 url => " + url);
        if(!super.isAccessAllowed(servletRequest,servletResponse,o)){
            //return  true;
            servletRequest.setAttribute("islogin","yes");
            return false;
        }

        return subject.isPermitted(url);
    }

    /**
     * onAccessDenied：表示当访问拒绝时是否已经处理了；如果返回 true 表示需要继续处理；如果返回 false 表示该拦截器实例已经处理了，将直接返回即可。

     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        if(request.getAttribute("islogin")!=null){
            jsonObject.put("code",401);
            jsonObject.put("msg","请重新登陆");
        }else {
            jsonObject.put("code", 403);
            jsonObject.put("msg", "没有权限");
        }
        PrintWriter out = null;
        HttpServletResponse res = (HttpServletResponse) response;
        try {
            res.setCharacterEncoding("utf-8");
            res.setContentType("application/json");
            out = response.getWriter();
            out.println(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
        return false;
    }
    private void setHeader(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        httpServletResponse.setHeader("Content-Type","application/json;charset=UTF-8");
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader(HttpHeaders.ORIGIN));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "platform");
    }
}
