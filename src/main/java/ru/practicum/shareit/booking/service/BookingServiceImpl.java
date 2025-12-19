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

        //check start not equal end
        if (booking.getStart().equals(booking.getEnd())) {
            throw new InvalidArgumentException("время начала бронирования должно быть до времени конца");
        }

        booking.setStatus(Status.WAITING);
        return BookingDtoMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDtoResponse findById(Long id, Long userId) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ConditionsNotMetException("такого бронирования нет"));
        if (!((booking.getItem().getOwner() == userId) || (Objects.equals(booking.getBooker().getId(), userId)))) {
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
        booking.setStatus((approved) ? Status.APPROVED : Status.REJECTED);
        return BookingDtoMapper.toBookingDto(bookingRepository.save(booking));
    }


    @Override
    public List<BookingDtoResponse> getAllBookingsByUserId(Long userId, String state) {
        //check if user exists
        userRepository.findById(userId)
                .orElseThrow(() -> new ConditionsNotMetException("нет такого пользователя"));

        return listFilter(bookingRepository.findByBookerId(userId), state);
    }

    @Override
    public List<BookingDtoResponse> getUserAllBooking(Long userId, String state) {
        //check if user exists
        userRepository.findById(userId)
                .orElseThrow(() -> new ConditionsNotMetException("нет такого пользователя"));

        return listFilter(bookingRepository.getUserAllBooking(userId), state);
    }

    private List<BookingDtoResponse> listFilter(List<Booking> list, String state) {
        LocalDateTime now = LocalDateTime.now();
        switch (state.toUpperCase()) {
            case "CURRENT" -> {
                return list.stream().filter(booking ->
                                (booking.getStart().isBefore(now) && booking.getEnd().isAfter(now)))
                        .map(BookingDtoMapper::toBookingDto)
                        .toList();
            }
            case "PAST" -> {
                return list.stream().filter(booking -> booking.getEnd().isBefore(now))
                        .map(BookingDtoMapper::toBookingDto)
                        .toList();
            }
            case "FUTURE" -> {
                return list.stream().filter(booking -> booking.getStart().isAfter(now))
                        .map(BookingDtoMapper::toBookingDto)
                        .toList();
            }
            case "WAITING" -> {
                return list.stream().filter(booking -> (booking.getStatus() == Status.WAITING))
                        .map(BookingDtoMapper::toBookingDto)
                        .toList();
            }
            case "REJECTED" -> {
                return list.stream().filter(booking -> (booking.getStatus() == Status.REJECTED))
                        .map(BookingDtoMapper::toBookingDto)
                        .toList();
            }
        }
        return list.stream().map(BookingDtoMapper::toBookingDto).toList();
    }

}
