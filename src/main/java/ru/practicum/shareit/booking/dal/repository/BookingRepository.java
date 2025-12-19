package ru.practicum.shareit.booking.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookerId(Long userId);
    @Query("select it from Booking as it " +
            "join it.booker as b " +
            "join it.item as i " +
            "where i.id in (select im.id from Item  as im where im.owner = ?1)")
    List<Booking> getUserAllBooking(Long userId);
    List<Booking> findByBookerIdAndItemId(Long bookerId, Long UserId);
}
