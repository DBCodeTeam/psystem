package com.um.psystem.utils;

import com.um.psystem.common.Const;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import com.um.psystem.authorization.*;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author: zzj
 * @Description: 权限工具类
 * @Date: 2020/5/22
 */
public class AuthUtils {

    private static Logger logger = LoggerFactory.getLogger(AuthUtils.class);

    public static void reloadAuthorizing(UserAuthRealm myRealm,String username){
        Subject subject = SecurityUtils.getSubject();

        String realmName = subject.getPrincipals().getRealmNames().iterator().next();
        //第一个参数为用户名,第二个参数为realmName,test想要操作权限的用户
        SimplePrincipalCollection principals = new SimplePrincipalCollection(username,realmName);
        subject.runAs(principals);
        myRealm.getAuthorizationCache().remove(subject.getPrincipals());
        subject.releaseRunAs();
    }

    public static void clearAuth(String username){
        RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();
        UserAuthRealm myRealm = (UserAuthRealm)rsm.getRealms().iterator().next();

        Subject subject = SecurityUtils.getSubject();
        String realmName = subject.getPrincipals().getRealmNames().iterator().next();
        //username:用户名
        SimplePrincipalCollection principals = new SimplePrincipalCollection(username, realmName);
        //切换身份
        subject.runAs(principals);
        //删除缓存
        myRealm.getAuthorizationCache().remove(subject.getPrincipals());
        //切回上一身份
        subject.releaseRunAs();

        myRealm.clearAuthz();
    }

    public static void refreshOwnAuthorizing(){
        RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();
        UserAuthRealm myRealm = (UserAuthRealm)rsm.getRealms().iterator().next();
        Subject subject = SecurityUtils.getSubject();
        myRealm.getAuthorizationCache().remove(subject.getPrincipals());

        logger.info("oper.user="+subject.getPrincipals().toString()+ ",login.user="+SecurityUtils.getSubject().getPrincipal().toString());

    }

    public static void refreshAuthorizing(String username){
        RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();
        UserAuthRealm myRealm = (UserAuthRealm)rsm.getRealms().iterator().next();
        myRealm.clearAuthorizationInfo(username);
        /*Subject subject = SecurityUtils.getSubject();
        String realmName = subject.getPrincipals().getRealmNames().iterator().next();
        if("admin".equals(username)){
            myRealm.getAuthorizationCache().remove(subject.getPrincipals());
        }else{
            SimplePrincipalCollection principals = new SimplePrincipalCollection(username, realmName);
            subject.runAs(principals);
            myRealm.getAuthorizationCache().remove(subject.getPrincipals());
            subject.releaseRunAs();
        }*/

        //System.out.println(myRealm.getAuthorizationCache());

        //myRealm.clearAllCache();
        //myRealm.getAuthorizationCache().remove(subject.getPrincipals());
        //subject.releaseRunAs();

        //subject.getPreviousPrincipals();//得到切换身份之前的身份;
        logger.info("oper.user=admin login.user="+SecurityUtils.getSubject().getPrincipal().toString());

    }
}
