package pro.likada.util.security;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import pro.likada.model.User;
import pro.likada.service.UserService;

import javax.inject.Inject;

/**
 * Created by Yusupov on 2/7/2017.
 */
public class JpaRealm extends AuthorizingRealm {

    private static String REALM_NAME = "empty";
    private User currentUser;

    @Inject
    private UserService userService;

    public JpaRealm() {
        setName(REALM_NAME); // This name must match the name in the User class's getPrincipals() method
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        String username = (String)authToken.getPrincipal();
        currentUser = userService.findByUsername(username);
        if(currentUser == null)
            throw new UnknownAccountException();
        if(Boolean.FALSE.equals(currentUser.getActive()))
            throw new LockedAccountException();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                currentUser.getUsername(),
                currentUser.getPassword(),
                null,//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if(currentUser==null)
            currentUser = userService.findByUsername(username);
        authorizationInfo.setRoles(userService.findRoles(currentUser));
        authorizationInfo.setStringPermissions(userService.findPermissions(currentUser));
        return authorizationInfo;
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
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
