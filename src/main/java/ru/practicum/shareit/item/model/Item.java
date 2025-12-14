package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * TODO Sprint add-controllers.
 */
@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class Item {
    Long id;
    String name;
    String description;
    Boolean available;
    long owner;
    long request;

    public Boolean isAvailable() {
        return available;
    }
}
