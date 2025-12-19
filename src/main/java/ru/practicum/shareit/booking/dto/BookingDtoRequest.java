package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class BookingDtoRequest {
    Long id;
    @NotNull(message = "не указана дата и время начала бронирования")
    //@FutureOrPresent(message = "дата начала бронирования не должна быть в прошедшем")
    LocalDateTime start;
    @NotNull(message = "не указана дата и время конца бронирования")
    //@FutureOrPresent(message = "дата конца бронирования не должна быть в прошедшем")
    LocalDateTime end;
    @NotNull(message = "не указана вещь, которую пользователь бронирует")
    Long itemId;
}
