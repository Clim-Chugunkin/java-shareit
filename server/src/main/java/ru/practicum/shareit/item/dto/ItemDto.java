package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * TODO Sprint add-controllers.
 */
@Value
@AllArgsConstructor
@Builder(toBuilder = true)
public class ItemDto {
    Long id;
    String name;
    String description;
    Boolean available;
    Long requestId;
}
