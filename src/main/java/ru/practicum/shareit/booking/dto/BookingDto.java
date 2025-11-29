package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class BookingDto {
    Long id;
    @NotNull(message = "не указана дата и время начала бронирования")
    LocalDateTime start;
    @NotNull(message = "не указана дата и время конца бронирования")
    LocalDateTime end;
    @NotNull(message = "не указана вещь, которую пользователь бронирует")
    Long item;
    @NotNull(message = "не указан пользователь, который осуществляет бронирование")
    Long booker;
    Status status;
}
