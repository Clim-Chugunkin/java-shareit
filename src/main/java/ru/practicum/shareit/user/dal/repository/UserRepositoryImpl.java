package ru.practicum.shareit.user.dal.repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.BadEmailException;
import ru.practicum.shareit.exception.ConditionsNotMetException;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public List<User> getUsers() {
        return new ArrayList<User>(users.values());
    }

    @Override
    public User addUser(User user) {
        User newUser = user.toBuilder()
                .id(getNextId())
                .build();
        checkUserEmail(newUser);
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public User update(User user) {
        User oldUser = getUserById(user.getId());
        //check new email
        if (user.getEmail() != null) {
            checkUserEmail(user);
        }

        User userUpdated = oldUser.toBuilder()
                .name((user.getName() != null) ? user.getName() : oldUser.getName())
                .email((user.getEmail() != null) ? user.getEmail() : oldUser.getEmail())
                .build();

        users.put(userUpdated.getId(), userUpdated);
        return userUpdated;
    }

    @Override
    public User getUserById(long id) {
        User user = users.get(id);
        if (user == null) {
            throw new ConditionsNotMetException("такого пользовтеля нет");
        }
        return user;
    }

    @Override
    public void userRemove(long id) {
        users.remove(id);
    }

    // вспомогательный метод для генерации идентификатора нового поста
    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    public void checkUserEmail(User user) {
        users.values()
                .forEach((oldUser) -> {
                    if (oldUser.getEmail().equals(user.getEmail())) {
                        throw new BadEmailException("Такой email уже есть");
                    }
                });
    }
}
