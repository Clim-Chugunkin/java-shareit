package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConditionsNotMetException;
import ru.practicum.shareit.user.dal.repository.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public User getUserById(long id) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new ConditionsNotMetException("такого пользовтеля нет");
        }
        return user;
    }

    public User addUser(User newUser) {
        User user = userRepository.addUser(newUser);
        log.info("Добавлен новый пользователь {}", user.getName());
        return user;
    }

    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        return userRepository.update(user);
    }

    public void removeUser(long id) {
        User user = userRepository.getUserById(id);
        userRepository.userRemove(id);
        log.info("Пользователь {} удален", user.getName());
    }

}
