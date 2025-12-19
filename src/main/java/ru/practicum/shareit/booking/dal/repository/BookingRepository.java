package ru.practicum.shareit.booking.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookerId(Long userId);

    @Query("select it from Booking as it " +
            "join it.booker as b " +
            "join it.item as i " +
            "where i.id in (select im.id from Item  as im where im.owner = ?1)")
    List<Booking> getUserAllBooking(Long userId);

    List<Booking> findByBookerIdAndItemId(Long bookerId, Long userId);

    @Query("select MAX(it.end) from Booking as it " +
            "join it.item as i " +
            "where i.id = ?1 ")
    LocalDateTime findLastBooking(Long id);

    @Query("select MIN(it.start) from Booking as it " +
            "join it.item as i " +
            "where i.id = ?1 " +
            "and it.start > CURRENT_TIMESTAMP")
    LocalDateTime findNextBooking(Long id);


}
