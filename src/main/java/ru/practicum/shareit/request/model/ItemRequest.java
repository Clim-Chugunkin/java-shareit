package ru.practicum.shareit.request.model;

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
public class ItemRequest {
    Long id;
    String description;
    Long requestor;
    LocalDateTime created;
}
