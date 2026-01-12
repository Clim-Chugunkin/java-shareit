package ru.practicum.shareit.user.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
    private final UserService userService;

    @Test
    public void saveAndGetUserTest() {
        User user = generateTestUser();
        Long userId = userService.addUser(user).getId();
        TypedQuery<User> query = em.createQuery("Select u from User u where u.id = :id", User.class);
        User us = query.setParameter("id", userId)
                .getSingleResult();
        assertEquals(user.getEmail(), us.getEmail());
    }

    @Test
    public void deleteUser() {
        User user = generateTestUser();
        em.persist(user);
        Long userId = user.getId();
        assertNotNull(userId);
        userService.removeUser(userId);
        assertThrows(ConditionsNotMetException.class, () -> {
            userService.getUserById(userId);
        });
    }

    @Test
    public void updateUserTest() {
        User user = generateTestUser();
        em.persist(user);
        Long userId = user.getId();
        assertNotNull(userId);
        User newUser = new User();
        newUser.setId(userId);
        newUser.setEmail("newUser@mail.ru");
        userService.updateUser(newUser);
        TypedQuery<User> query = em.createQuery("Select u from User u where u.id = :id", User.class);
        user = query.setParameter("id", userId)
                .getSingleResult();
        assertEquals("newUser@mail.ru", user.getEmail(), "wrong email");
    }

    public static User generateTestUser() {
        User user = new User();
        user.setName("NoName");
        user.setEmail("NoName@mail.ru");
        return user;
    }
}