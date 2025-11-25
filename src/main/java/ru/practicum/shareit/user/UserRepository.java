package ru.practicum.shareit.user;

import java.util.List;

public interface UserRepository {
    List<User> getUsers();

    User addUser(User user);

    User update(User user);

    User getUserById(long id);

    void userRemove(long id);
}
