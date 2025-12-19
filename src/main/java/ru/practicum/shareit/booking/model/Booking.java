package ru.practicum.shareit.booking.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Entity
@Table(name = "bookings", schema = "public")
@Getter
@Setter
@ToString
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime start;
    @Column(name = "stop")
    LocalDateTime end;
    @ManyToOne
    @JoinColumn(name="item_id")
    Item item;
    @ManyToOne
    @JoinColumn(name="user_id")
    User booker;
    @Enumerated(EnumType.STRING)
    Status status;
}
