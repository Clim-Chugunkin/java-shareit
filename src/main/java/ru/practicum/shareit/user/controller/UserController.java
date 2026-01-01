package ru.practicum.shareit.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dal.mapper.UserDtoMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers().stream()
                .map(UserDtoMapper::toUserDto)
                .toList();
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {

        return UserDtoMapper.toUserDto(userService.getUserById(userId));
    }

    @PostMapping
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        return UserDtoMapper.toUserDto(userService.addUser(UserDtoMapper.toUser(userDto)));
    }

    @PatchMapping("/{userId}")
    public UserDto update(@PathVariable long userId,
                          @RequestBody UserDto userDto) {
        User user = UserDtoMapper.toUser(userDto);
        user.setId(userId);
        return UserDtoMapper.toUserDto(userService.updateUser(user));
    }

    @DeleteMapping("/{userId}")
    public void removeUser(@PathVariable long userId) {
        userService.removeUser(userId);
    }

}
