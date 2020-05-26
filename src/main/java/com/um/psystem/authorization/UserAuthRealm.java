package com.um.psystem.authorization;

import com.alibaba.fastjson.JSON;
import com.um.psystem.common.Const;
import com.um.psystem.entity.User;
import com.um.psystem.model.response.ResourceResponse;
import com.um.psystem.model.response.RoleResponse;
import com.um.psystem.model.response.UserResponse;
import com.um.psystem.service.IResourceService;
import com.um.psystem.service.IRoleService;
import com.um.psystem.service.IUserService;
import com.um.psystem.utils.BeanCopier;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Function:shiro权限控制. <br/>
 */
@Service("userAuthRealm")
public class UserAuthRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthRealm.class);

    @Resource
    private IUserService userService;

    @Resource
    private IResourceService resourceService;

    @Resource
    private IRoleService roleService;

    public UserAuthRealm(){

        System.out.println("=============初始化realm==============");
        super.setName(Const.MY_REALM);

        setCachingEnabled(true);

        /* 允许认证缓存 */
        setAuthenticationCachingEnabled(true);
        setAuthenticationCacheName(Const.AUTH_REALM_CACHE);

        /* 允许授权缓存 */
        setAuthorizationCachingEnabled(true);
        setAuthorizationCacheName(Const.PERM_REALM_CACHE);


       //super.setAuthenticationCachingEnabled(true);
       //super.setAuthenticationCacheName(Const.AUTH_REALM_CACHE);
       //super.setAuthorizationCachingEnabled(true);
       //super.setAuthorizationCacheName(Const.PERM_REALM_CACHE);

    }

    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        User user = userService.getUser(token.getUsername());
        if (user != null) {
            this.initSession(user);
            SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), user.getUsername());
            return authInfo;
        } else {
            return null;
        }
    }

    /**
     * 授权查询回调函数, 进行鉴权当缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        String realmName = SecurityUtils.getSubject().getPrincipals().getRealmNames().iterator().next();
        /**
         * 把principals放session中 key=userId value=principals
         */
        SecurityUtils.getSubject().getSession().setAttribute(username, SecurityUtils.getSubject().getPrincipals());
        SimpleAuthorizationInfo info = this.authorize(username);
        return info;
    }

    /**
     * 设置用户session
     */
    private void initSession(User user) {
        Session session = SecurityUtils.getSubject().getSession();

        session.setTimeout(-1000l);// timeout:-1000ms 永不超时

        UserResponse userResponse = BeanCopier.copy(user, UserResponse.class);

        session.setAttribute(Const.SESSION_USER, userResponse);
    }

    /**
     * 为用户授权.
     *
     * @param username
     * @return
     */
    private SimpleAuthorizationInfo authorize(String username) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        /**
         * 赋予角色
         */
        List<RoleResponse> roles = roleService.getRoles(username);
        for (RoleResponse role : roles) {
            if(role!=null){
                info.addRole(role.getCode());
            }
        }

        /**
         * 赋予权限
         */
        List<ResourceResponse> permissions = resourceService.getResourceByUsername(username, null);
        for (ResourceResponse permission : permissions) {
            if (permission!=null && StringUtils.isNotEmpty(permission.getCode())) {
                info.addStringPermission(permission.getCode());
            }
        }
        logger.info("roles:" + JSON.toJSONString(info.getRoles()));
        logger.info("permission:" + JSON.toJSONString(info.getStringPermissions()));
        return info;
    }

    /**
     * 设定Password校验.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        /**
         * 自定义密码验证
         */
        setCredentialsMatcher(new CustomizedCredentialsMatcher());
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);

    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        this.clearAllCachedAuthenticationInfo();
        this.clearAllCachedAuthorizationInfo();
    }

    public void clearAuthz(){
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }

}
