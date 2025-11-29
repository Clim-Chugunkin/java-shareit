package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class ItemRequestDto {
    Long id;
    @NotNull(message = "не указано описание")
    @NotEmpty(message = "описание не должно быть пустым")
    String description;
    @NotNull(message = "не указан пользователь, создавший запрос")
    Long requestor;
    @NotNull(message = "не указана дата и время создания запроса")
    LocalDateTime created;
}