package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;


public interface BookingService {
   BookingDtoResponse addBooking(Booking booking);
   BookingDtoResponse findById(Long id, Long UserId);
   BookingDtoResponse approveBooking(long userId, long bookingId, boolean approved);
   List<BookingDtoResponse> getAllBookingsByUserId(Long userId, String state);
   List<BookingDtoResponse> getUserAllBooking(Long userId, String state);
}
