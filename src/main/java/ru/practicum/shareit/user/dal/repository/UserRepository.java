package ru.practicum.shareit.user.dal.repository;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepository {
    List<User> getUsers();

    User addUser(User user);

    User update(User user);

    User getUserById(long id);

    void userRemove(long id);
}
