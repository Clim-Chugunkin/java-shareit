package ru.practicum.shareit.booking.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    //ALL
    List<Booking> findByBookerId(Long userId);

    //CURRENT
    @Query("select it from Booking as it " +
            "join it.booker as b " +
            "join it.item as i " +
            "where b.id = ?1 " +
            "and it.start < CURRENT_TIMESTAMP " +
            "and it.end > CURRENT_TIMESTAMP")
    List<Booking> findByBookerIdAndCurrent(Long userId);

    //PAST
    List<Booking> findByBookerIdAndEndBefore(Long userId, LocalDateTime date);

    //FUTURE
    List<Booking> findByBookerIdAndStartAfter(Long userId, LocalDateTime date);

    //STATUS
    List<Booking> findByBookerIdAndStatus(Long userId, Status status);


    //ALL
    @Query("select it from Booking as it " +
            "join it.booker as b " +
            "join it.item as i " +
            "where i.id in (select im.id from Item  as im where im.owner = ?1)")
    List<Booking> getUserAllBooking(Long userId);

    //CURRENT
    @Query("select it from Booking as it " +
            "join it.booker as b " +
            "join it.item as i " +
            "where i.id in (select im.id from Item  as im where im.owner = ?1) " +
            "and it.start < CURRENT_TIMESTAMP " +
            "and it.end > CURRENT_TIMESTAMP")
    List<Booking> getUserAllBookingCurrent(Long userId);

    //PAST
    @Query("select it from Booking as it " +
            "join it.booker as b " +
            "join it.item as i " +
            "where i.id in (select im.id from Item  as im where im.owner = ?1) " +
            "and it.end < CURRENT_TIMESTAMP")
    List<Booking> getUserAllBookingPast(Long userId);

    //FUTURE
    @Query("select it from Booking as it " +
            "join it.booker as b " +
            "join it.item as i " +
            "where i.id in (select im.id from Item  as im where im.owner = ?1) " +
            "and it.start > CURRENT_TIMESTAMP")
    List<Booking> getUserAllBookingFuture(Long userId);

    @Query("select it from Booking as it " +
            "join it.booker as b " +
            "join it.item as i " +
            "where i.id in (select im.id from Item  as im where im.owner = ?1) " +
            "and it.status = ?2")
    List<Booking> getUserAllBookingStatus(Long userId, Status status);

    @Query("select count(it) from Booking as it " +
            "join it.booker as b " +
            "join it.item as i " +
            "where b.id = ?1 " +
            "and i.id = ?2 " +
            "and it.start<=?4 " +
            "and it.end>=?3")
    long checkIntersection(Long userId, Long itemId, LocalDateTime start, LocalDateTime stop);

    List<Booking> findByBookerIdAndItemId(Long bookerId, Long userId);
}
