package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUsers(){
        return userRepository.getUsers();
    }

    public User getUserById(long id){
        return userRepository.getUserById(id);
    }

    public User addUser(User newUser){
        User user  = userRepository.addUser(newUser);
        log.info("Добавлен новый пользователь {}", user.getName());
        return user;
    }

    public User updateUser(User user){
        return userRepository.update(user);
    }

    public void removeUser(long id){
        User user = userRepository.getUserById(id);
        userRepository.userRemove(id);
        log.info("Пользователь {} удален", user.getName());
    }

}
