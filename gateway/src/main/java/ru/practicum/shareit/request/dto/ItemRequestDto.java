package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@AllArgsConstructor
public class ItemRequestDto {
    @NotNull(message = "не указано описание")
    @NotEmpty(message = "описание не должно быть пустым")
    String description;
}