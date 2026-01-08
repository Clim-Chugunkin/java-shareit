package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;

import java.util.List;


public interface UserService {
    List<User> getUsers();

    User getUserById(long id);

    User addUser(User newUser);

    User updateUser(User user);

    void removeUser(long id);
}
