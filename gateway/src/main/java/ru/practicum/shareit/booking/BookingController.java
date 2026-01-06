package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingClient bookingClient;


    @PostMapping
    public ResponseEntity<Object> addBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                             @Valid @RequestBody BookingDtoRequest request) {
        return bookingClient.addBooking(userId, request);
    }

    @GetMapping
    public ResponseEntity<Object> getAllBookings(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @RequestParam(name = "state", defaultValue = "ALL") String state) {
        return bookingClient.getAllBookings(userId, state);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getUserAllBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                    @RequestParam(name = "state", defaultValue = "ALL") String state) {

        return bookingClient.getUserAllBooking(userId, state);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> findById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                           @PathVariable(name = "bookingId") long bookingId) {
        return bookingClient.findById(bookingId, userId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> bookingApprove(@RequestHeader("X-Sharer-User-Id") long userId,
                                                 @PathVariable(name = "bookingId") long bookingId,
                                                 @RequestParam Boolean approved) {
        return bookingClient.bookingApprove(userId, bookingId, approved);
    }
}
