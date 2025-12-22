package ru.practicum.shareit.booking.dal.mapper;

import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class BookingDtoMapper {

    public static BookingDtoResponse toBookingDto(Booking booking) {
        return BookingDtoResponse.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(booking.getItem())
                .booker(booking.getBooker())
                .status(booking.getStatus())
                .build();
    }

    public static Booking toBooking(BookingDtoRequest bookingDto, Long userId) {
        Booking booking = new Booking();
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        Item item = new Item();
        item.setId(bookingDto.getItemId());
        booking.setItem(item);
        User booker = new User();
        booker.setId(userId);
        booking.setBooker(booker);
        return booking;
    }
}
