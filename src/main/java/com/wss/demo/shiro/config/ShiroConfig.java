package com.wss.demo.shiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import com.wss.demo.shiro.authenticator.MyModularRealmAuthenticator;
import com.wss.demo.shiro.dao.MySessionDao;
import com.wss.demo.shiro.filter.ResourceCheckFilter;
import com.wss.demo.shiro.permission.UrlPermissionResolver;
import com.wss.demo.shiro.realm.CustomRealm;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAPP=new DefaultAdvisorAutoProxyCreator();
        defaultAPP.setProxyTargetClass(true);
        return defaultAPP;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor=new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public CustomRealm myShiroRealm(){
        CustomRealm customReal=new CustomRealm();
        HashedCredentialsMatcher md5CredentialMather= new HashedCredentialsMatcher();
        md5CredentialMather.setHashIterations(20);
        md5CredentialMather.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
        customReal.setCredentialsMatcher(md5CredentialMather);
        customReal.setPermissionResolver(new UrlPermissionResolver());
        customReal.setCachingEnabled(true);
        customReal.setAuthenticationCacheName("authenticationCache");
        customReal.setAuthenticationCachingEnabled(true);
        customReal.setAuthorizationCacheName("authorizationCache");
        customReal.setAuthorizationCachingEnabled(true);
        return customReal;
    }



    @Bean
    public SecurityManager securityManager(RedisTemplate<String,Object> redisTemplate){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        List<Realm> realmList=new LinkedList<Realm>();
        realmList.add(myShiroRealm());

        securityManager.setRealms(realmList);
        //ModularRealmAuthenticator authenticator=new ModularRealmAuthenticator();
        ModularRealmAuthenticator authenticator=new MyModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        authenticator.setRealms(realmList);

        securityManager.setAuthenticator(authenticator);
        DefaultWebSessionManager sessionManager=new DefaultWebSessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(true);
        sessionManager.setGlobalSessionTimeout(600000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionValidationInterval(600000);
        MySessionDao sessionDao =new MySessionDao(redisTemplate);
        sessionDao.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
        sessionManager.setSessionDAO(sessionDao);
        securityManager.setSessionManager(sessionManager);
        EhCacheManager cacheManager=new EhCacheManager();
        //cacheManager.setCacheManagerConfigFile("C:\\Users\\shuifan\\IdeaProjects\\Login\\src\\main\\resources\\ecache.xml");
        cacheManager.setCacheManagerConfigFile("/home/ubuntu/module/WS/ecache.xml");
        //securityManager.setCacheManager(cacheManager);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        Map<String, Filter> filterMap=new HashMap<>();
        ResourceCheckFilter filter=new ResourceCheckFilter();
        filterMap.put("authc",filter);
        shiroFilterFactoryBean.setFilters(filterMap);

        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String,String> map=new HashMap<String, String>();
        map.put("/logout","anon");
        map.put("/login*","anon");
        map.put("/error*","anon");
        map.put("/**","authc");
        shiroFilterFactoryBean.setLoginUrl("login.html");
        shiroFilterFactoryBean.setSuccessUrl("/index.html");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error.html");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

}
