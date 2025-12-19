package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import ru.practicum.shareit.item.model.Comment;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Value
@AllArgsConstructor
@Builder(toBuilder = true)
public class ItemDto {
    Long id;
    @NotNull(message = "не указано имя")
    @NotEmpty(message = "имя не должно быть пустым")
    String name;
    @NotNull(message = "не указано описание")
    @NotEmpty(message = "описание не должно быть пустым")
    String description;
    @NotNull(message = "не указана доступность")
    Boolean available;
    LocalDateTime lastBooking;
    LocalDateTime nextBooking;
    List<Comment> comments;
}
