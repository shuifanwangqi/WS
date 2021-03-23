package com.wss.demo.shiro.authenticator;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;
import java.util.Iterator;

public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {

    /**
     *  扩展父类原方法，对异常立即进行外抛
     * @param realms
     * @param token
     * @return
     */
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm>
                                                                    realms, AuthenticationToken token) {
        AuthenticationStrategy strategy = this.getAuthenticationStrategy();
        AuthenticationInfo aggregate = strategy.beforeAllAttempts(realms, token);

        Iterator var5 = realms.iterator();
        AuthenticationException authenticationException = null;
        while(var5.hasNext()) {
            Realm realm = (Realm)var5.next();
            aggregate = strategy.beforeAttempt(realm, token, aggregate);
            if (realm.supports(token)) {

                AuthenticationInfo info = null;
                Throwable t = null;
                try {
                    info = realm.getAuthenticationInfo(token);
                } catch (Throwable var11) {
                    t = var11;
                    authenticationException = (AuthenticationException)var11;

                }
                aggregate = strategy.afterAttempt(realm, token, info, aggregate, t);

                if (authenticationException != null){
                    throw authenticationException;
                }
            } else {

            }
        }

        aggregate = strategy.afterAllAttempts(token, aggregate);
        return aggregate;
    }
}