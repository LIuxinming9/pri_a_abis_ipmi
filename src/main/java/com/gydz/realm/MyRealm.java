package com.gydz.realm;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.gydz.user.model.User;
import com.gydz.user.service.UserServiceImpl;

public class MyRealm extends AuthorizingRealm {
	
    @Resource
    private UserServiceImpl userServiceImpl;
 
    //
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo  authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userServiceImpl.getRoles(username));
        authorizationInfo.setStringPermissions(userServiceImpl.getPermissions(username));
        return authorizationInfo;
    }
    
    //
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) 
    		throws AuthenticationException {
    	System.out.println("token:"+token);
    	String username = (String) token.getPrincipal();//
        System.out.println("username:"+username);
        User user = userServiceImpl.getByUsername(username);   
        if(user!=null){System.out.println("user:"+user.getUsername());
            AuthenticationInfo authcInfo =new SimpleAuthenticationInfo(
            		user.getAccount(),user.getPassword(),"myRealm");
            return authcInfo;
        }else{
            return null;
        }
    }
}

