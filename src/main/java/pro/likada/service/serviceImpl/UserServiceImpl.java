package pro.likada.service.serviceImpl;

import pro.likada.dao.UserDAO;
import pro.likada.model.Permission;
import pro.likada.model.Role;
import pro.likada.model.User;
import pro.likada.service.UserService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Yusupov on 12/14/2016.
 */
@Named("userService")
@Transactional
public class UserServiceImpl implements UserService, Serializable{

    @Inject
    private UserDAO userDAO;

    @Override
    public User findById(long id) {
        return userDAO.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        userDAO.save(user);
    }

    @Override
    public void deleteUserByUsername(String username) {
        userDAO.deleteByUsername(username);
    }

    @Override
    public List<User> findAllUsersNotAddedToTable(String searchString, Set<User> customerManagers) {
        return userDAO.findAllUsersNotAddedToTable(searchString, customerManagers);
    }

    @Override
    public List<User> findByRole(String roleType){
        return  userDAO.findByRole(roleType);
    }

    @Override
    public Set<String> findRoles(User user) {
        Set<String> roles = new HashSet<String>();
        for(Role role: user.getUserRoles())
            roles.add(role.getType());
        return roles;
    }

    @Override
    public Set<String> findPermissions(User user) {
        Set<String> permissions = new HashSet<String>();
        for(Permission permission: user.getUserPermissions())
            permissions.add(permission.getPermission());
        return permissions;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public String getStringOfUserRoles(List<Role> roles) {
        StringBuilder sb = new StringBuilder();
        for(Role userRole : roles){
            sb.append(userRole.getName());
            sb.append(',');
        }
        if(sb.length()>0){
            sb.delete(sb.length()-1,sb.length());
        }
        return sb.toString();
    }


}
