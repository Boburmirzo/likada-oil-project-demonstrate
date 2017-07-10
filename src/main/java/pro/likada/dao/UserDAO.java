package pro.likada.dao;

import pro.likada.model.User;

import java.util.List;
import java.util.Set;

/**
 * Created by Yusupov on 12/14/2016.
 */
public interface UserDAO {

    User findById(long id);

    User findByUsername(String username);

    void save(User user);

    void deleteByUsername(String username);

    List<User> findAllUsersNotAddedToTable(String searchString, Set<User> customerManagers);

    void changePassword(long userId, String newPassword);

    List<User> findByRole(String roleType);

    List<User> getAllUsers();
}
