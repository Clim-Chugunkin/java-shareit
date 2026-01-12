package ru.practicum.shareit.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
public class ErrorMessage {
    private final String error;
    private final LocalDateTime timestamp;
}
