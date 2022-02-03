package sample.services.user;

import sample.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> users();
    Optional<User> getUser(String username, String password);
    Optional<User> add(String username, String password);
}
