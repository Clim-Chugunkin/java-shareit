package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class UserDto {
    Long id;
    @NotNull(message = "не указана почта")
    @Email(message = "почта указана неправильно")
    String email;
    @NotNull(message = "не указано имя")
    String name;
}
