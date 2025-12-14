package ru.practicum.shareit.exception;

public class BadEmailException extends RuntimeException {
    public BadEmailException(String message) {
        super(message);
    }
}
