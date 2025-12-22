package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class BookingDtoResponse {
    Long id;
    LocalDateTime start;
    LocalDateTime end;
    Item item;
    User booker;
    Status status;
}
