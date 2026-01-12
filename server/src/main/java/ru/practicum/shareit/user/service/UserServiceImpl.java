package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.BadEmailException;
import ru.practicum.shareit.exception.ConditionsNotMetException;
import ru.practicum.shareit.user.dal.repository.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ConditionsNotMetException("пользователь не найден"));
    }

    public User addUser(User newUser) {
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new BadEmailException("такой емаил уже есть");
        }

        User user = userRepository.save(newUser);
        log.info("Добавлен новый пользователь {}", user.getName());
        return user;
    }

    public User updateUser(User newUser) {
        User user = userRepository.findById(newUser.getId()).orElseThrow(() -> new ConditionsNotMetException("нет такого пользователя"));
        if (newUser.getEmail() != null) {
            if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
                throw new BadEmailException("такой емаил уже есть");
            }
            user.setEmail(newUser.getEmail());
        }
        user.setName((newUser.getName() == null) ? user.getName() : newUser.getName());
        return userRepository.save(user);
    }

    public void removeUser(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ConditionsNotMetException("пользователь не найден"));
        userRepository.deleteById(id);
        log.info("Пользователь {} удален", user.getName());
    }

}
