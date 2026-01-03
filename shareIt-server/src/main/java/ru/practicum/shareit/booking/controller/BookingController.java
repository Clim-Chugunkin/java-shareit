package ru.practicum.shareit.booking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dal.mapper.BookingDtoMapper;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDtoResponse addBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @Valid @RequestBody BookingDtoRequest request) {
        return bookingService.addBooking(BookingDtoMapper.toBooking(request, userId));
    }

    @GetMapping
    public List<BookingDtoResponse> getAllBookings(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                   @RequestParam(name = "state", defaultValue = "ALL") String state) {
        return bookingService.getAllBookingsByUserId(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDtoResponse> getUserAllBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                      @RequestParam(name = "state", defaultValue = "ALL") String state) {

        return bookingService.getUserAllBooking(userId, state);
    }

    @GetMapping("/{bookingId}")
    public BookingDtoResponse findById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                       @PathVariable(name = "bookingId") long bookingId) {
        return bookingService.findById(bookingId, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoResponse bookingApprove(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable(name = "bookingId") long bookingId,
                                             @RequestParam Boolean approved) {
        return bookingService.approveBooking(userId, bookingId, approved);
    }
}
