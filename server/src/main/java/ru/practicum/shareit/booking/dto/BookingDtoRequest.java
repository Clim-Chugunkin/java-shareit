package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class BookingDtoRequest {
    Long id;
    LocalDateTime start;
    LocalDateTime end;
    Long itemId;
}
