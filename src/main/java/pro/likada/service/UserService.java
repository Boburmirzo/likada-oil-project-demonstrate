package pro.likada.service;

import pro.likada.model.Role;
import pro.likada.model.User;

import java.util.List;
import java.util.Set;

/**
 * Created by Yusupov on 12/14/2016.
 */
public interface UserService {

    User findById(long id);

    User findByUsername(String username);

    void saveUser(User user);

    void deleteUserByUsername(String username);

    List<User> findAllUsersNotAddedToTable(String searchString, Set<User> customerManagers);

    List<User> findByRole(String roleType);

    Set<String> findRoles(User user);

    Set<String> findPermissions(User user);

    List<User> getAllUsers();

    String getStringOfUserRoles(List<Role> roles);

}
