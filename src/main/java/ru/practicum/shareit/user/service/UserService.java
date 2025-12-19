package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConditionsNotMetException;
import ru.practicum.shareit.user.dal.repository.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;


public interface UserService {
    List<User> getUsers();
    User getUserById(long id);
    User addUser(User newUser);
    User updateUser(User user);
    void removeUser(long id);
}
