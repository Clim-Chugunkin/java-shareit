package ru.practicum.shareit.user.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.ConditionsNotMetException;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImplTest {
    private final EntityManager em;
    private final UserServiceImpl userService;

    @Test
    public void saveAndGetUserTest() {
        User user = generateTestUser();
        Long userId = userService.addUser(user).getId();
        User us = userService.getUserById(userId);
        assertEquals(user.getEmail(), us.getEmail());
    }

    @Test
    public void deleteUser() {
        User user = generateTestUser();
        Long userId = userService.addUser(user).getId();
        assertNotNull(userId);
        userService.removeUser(userId);
        assertThrows(ConditionsNotMetException.class, () -> {
            userService.getUserById(userId);
        });
    }

    @Test
    public void updateUserTest() {
        User user = generateTestUser();
        Long userId = userService.addUser(user).getId();
        assertNotNull(userId);
        User newUser = new User();
        newUser.setId(userId);
        newUser.setEmail("newUser@mail.ru");
        userService.updateUser(newUser);
        user = userService.getUserById(userId);
        assertEquals("newUser@mail.ru", user.getEmail(), "wrong email");
    }

    public static User generateTestUser() {
        User user = new User();
        user.setName("NoName");
        user.setEmail("NoName@mail.ru");
        return user;
    }
}