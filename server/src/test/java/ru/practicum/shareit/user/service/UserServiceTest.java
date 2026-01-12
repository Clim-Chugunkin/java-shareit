package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.user.dal.mapper.UserDtoMapper;
import ru.practicum.shareit.user.dal.repository.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void saveUserTest() {
        UserDto user = UserDto.builder()
                .id(1L)
                .email("email")
                .name("Sergey")
                .build();
        Mockito
                .when(userRepository.save(Mockito.any()))
                .thenReturn(UserDtoMapper.toUser(user));

        User us = userService.addUser(UserDtoMapper.toUser(user));
        Mockito.verify(userRepository, Mockito.times(1))
                .save(UserDtoMapper.toUser(user));

        Assertions.assertEquals("email", us.getEmail(), "wrong email");
    }

    @Test
    void getUserByIdTest() {
        User user = new User();
        user.setId(1L);
        user.setName("NoName");
        user.setEmail("NoName@mail.ru");
        Mockito
                .when(userRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(user));

        User us = userService.getUserById(1L);
        Mockito.verify(userRepository, Mockito.times(1))
                .findById(1L);

        Assertions.assertEquals("NoName@mail.ru", us.getEmail(), "wrong email");
    }

}