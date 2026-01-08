package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.item.service.ItemServiceImplTest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserServiceImpl;
import ru.practicum.shareit.user.service.UserServiceImplTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingServiceImplTest {

    private final UserServiceImpl userService;
    private final ItemServiceImpl itemService;
    private final BookingServiceImpl bookingService;

    @Test
    public void addAndGetBookingTest() {
        User owner = UserServiceImplTest.generateTestUser();
        Long userId = userService.addUser(owner).getId();
        Item item = ItemServiceImplTest.generateItem(userId);
        item = itemService.addItem(item);
        User booker = UserServiceImplTest.generateTestUser();
        booker.setName("booker");
        booker.setEmail("booker@email");
        booker = userService.addUser(booker);
        Booking booking = new Booking();
        booking.setStart(LocalDateTime.now().plusSeconds(10));
        booking.setEnd(LocalDateTime.now().plusMinutes(1));
        booking.setItem(item);
        booking.setBooker(booker);
        Long bookingId = bookingService.addBooking(booking).getId();
        BookingDtoResponse bookingForTest = bookingService.findById(bookingId, booker.getId());
        assertNotNull(bookingForTest.getId());
    }

    @Test
    public void getAllBookingsByUserId() {
        User owner = UserServiceImplTest.generateTestUser();
        Long ownerId = userService.addUser(owner).getId();
        Item item = ItemServiceImplTest.generateItem(ownerId);
        item = itemService.addItem(item);

        Item item2 = ItemServiceImplTest.generateItem(ownerId);
        item2.setName("item2");
        item2.setDescription("description2");
        item2 = itemService.addItem(item2);

        User booker = UserServiceImplTest.generateTestUser();
        booker.setName("booker");
        booker.setEmail("booker@email");
        booker = userService.addUser(booker);

        Booking booking = new Booking();
        booking.setStart(LocalDateTime.now().plusSeconds(10));
        booking.setEnd(LocalDateTime.now().plusMinutes(1));
        booking.setItem(item);
        booking.setBooker(booker);
        bookingService.addBooking(booking);

        Booking booking2 = new Booking();
        booking2.setStart(LocalDateTime.now().plusSeconds(10));
        booking2.setEnd(LocalDateTime.now().plusMinutes(1));
        booking2.setItem(item2);
        booking2.setBooker(booker);
        bookingService.addBooking(booking2);

        List<BookingDtoResponse> list = bookingService.getAllBookingsByUserId(booker.getId(), "ALL");
        assertEquals(2, list.size());
    }

}