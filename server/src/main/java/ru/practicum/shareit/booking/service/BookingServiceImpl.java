package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dal.mapper.BookingDtoMapper;
import ru.practicum.shareit.booking.dal.repository.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.exception.ConditionsNotMetException;
import ru.practicum.shareit.exception.InvalidArgumentException;
import ru.practicum.shareit.item.dal.repository.ItemRepository;
import ru.practicum.shareit.user.dal.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public BookingDtoResponse addBooking(Booking booking) {
        //check if user exists
        userRepository.findById(booking.getBooker().getId())
                .orElseThrow(() -> new ConditionsNotMetException("нет такого пользователя"));
        //check if item exists
        booking.setItem(
                itemRepository.findById(booking.getItem().getId())
                        .orElseThrow(() -> new ConditionsNotMetException("нет такой вещи"))
        );

        //check if item unavailable
        if (!booking.getItem().getAvailable()) {
            throw new InvalidArgumentException("вещь недоступна");
        }

        //check if booker is owner
        if (Objects.equals(booking.getBooker().getId(), booking.getItem().getOwner())) {
            throw new InvalidArgumentException("владелец не может забронировать свою вещь");
        }

        //check start not equal end
        if (booking.getStart().equals(booking.getEnd())) {
            throw new InvalidArgumentException("время начала бронирования должно быть до времени конца");
        }

        //check intersection
        if (bookingRepository.checkIntersection(booking.getBooker().getId(),
                booking.getItem().getId(),
                booking.getStart(), booking.getEnd()) != 0) {
            throw new InvalidArgumentException("пересечение бронирования");
        }

        booking.setStatus(Status.WAITING);
        return BookingDtoMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDtoResponse findById(Long id, Long userId) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ConditionsNotMetException("такого бронирования нет"));
        if (!((Objects.equals(booking.getItem().getOwner(), userId)) || (Objects.equals(booking.getBooker().getId(), userId)))) {
            throw new InvalidArgumentException("пользователь не является владелцем/создателем");
        }

        return BookingDtoMapper.toBookingDto(bookingRepository.findById(id).orElseThrow(() -> new ConditionsNotMetException("такого бронирования нет")));
    }

    @Override
    public BookingDtoResponse approveBooking(long userId, long bookingId, boolean approved) {
        //check if booking exists
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ConditionsNotMetException("такой брони нет"));
        //check if user is item owner
        if (booking.getItem().getOwner() != userId) {
            throw new InvalidArgumentException("пользователь не является владельцем");
        }
        //Подтверждать по логике можно только бронирования в статусе ожидания
        if (booking.getStatus() != Status.WAITING) {
            throw new InvalidArgumentException("подтвердить бронирование можно только со статусом WAITING");
        }
        booking.setStatus((approved) ? Status.APPROVED : Status.REJECTED);
        return BookingDtoMapper.toBookingDto(bookingRepository.save(booking));
    }


    @Override
    public List<BookingDtoResponse> getAllBookingsByUserId(Long userId, String state) {
        //check if user exists
        userRepository.findById(userId)
                .orElseThrow(() -> new ConditionsNotMetException("нет такого пользователя"));

        LocalDateTime now = LocalDateTime.now();
        List<Booking> tempBooking =
                switch (state.toUpperCase()) {
                    case "CURRENT" -> bookingRepository.findByBookerIdAndCurrent(userId);
                    case "PAST" -> bookingRepository.findByBookerIdAndEndBefore(userId, now);
                    case "FUTURE" -> bookingRepository.findByBookerIdAndStartAfter(userId, now);
                    case "WAITING" -> bookingRepository.findByBookerIdAndStatus(userId, Status.WAITING);
                    case "REJECTED" -> bookingRepository.findByBookerIdAndStatus(userId, Status.REJECTED);
                    default -> bookingRepository.findByBookerId(userId);
                };
        return tempBooking.stream().map(BookingDtoMapper::toBookingDto).toList();
    }

    @Override
    public List<BookingDtoResponse> getUserAllBooking(Long userId, String state) {
        //check if user exists
        userRepository.findById(userId)
                .orElseThrow(() -> new ConditionsNotMetException("нет такого пользователя"));
        List<Booking> tempBooking =
                switch (state.toUpperCase()) {
                    case "CURRENT" -> bookingRepository.getUserAllBookingCurrent(userId);
                    case "PAST" -> bookingRepository.getUserAllBookingPast(userId);
                    case "FUTURE" -> bookingRepository.getUserAllBookingFuture(userId);
                    case "WAITING" -> bookingRepository.getUserAllBookingStatus(userId, Status.WAITING);
                    case "REJECTED" -> bookingRepository.getUserAllBookingStatus(userId, Status.REJECTED);
                    default -> bookingRepository.getUserAllBooking(userId);
                };
        return tempBooking.stream().map(BookingDtoMapper::toBookingDto).toList();
    }
}
