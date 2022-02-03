package sample.services.user.impl;

import sample.dao.impl.RoleDao;
import sample.dao.impl.UserDao;
import sample.model.Role;
import sample.model.Roles;
import sample.model.User;
import sample.services.user.UserService;
import sample.utils.EncoderDecoder;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;

    public UserServiceImpl() {
        this.roleDao = new RoleDao();
        this.userDao = new UserDao(roleDao);
    }

    @Override
    public List<User> users() {
        return userDao.users();
    }

    @Override
    public Optional<User> getUser(String username, String password){
        return userDao.getUserByUsernamePassword(username, EncoderDecoder.encode(password));
    }

    @Override
    public Optional<User> add(String username, String password){
        OptionalInt id = roleDao.getIdRoleByName(new Role(Roles.USER.name()));
        if (id.isPresent()){
            return userDao.add(username, EncoderDecoder.encode(password), id.getAsInt());
        }
        return Optional.empty();
    }
}
