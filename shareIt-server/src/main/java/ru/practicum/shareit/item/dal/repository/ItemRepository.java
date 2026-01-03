package ru.practicum.shareit.item.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOwner(Long ownerId);

    List<Item> findByNameContainingIgnoreCaseAndAvailable(String text, Boolean available);

    @Query("select new ru.practicum.shareit.item.dto.ItemDtoResponse(it" +
            //last booking
            ",(select MIN(it.end) from Booking as it " +
            " join it.item as i " +
            " where i.id = ?1 " +
            " and it.end > CURRENT_TIMESTAMP), " +
            //next booking
            "(select MIN(it.start) from Booking as it " +
            "join it.item as i " +
            "where i.id = ?1 " +
            "and it.start > CURRENT_TIMESTAMP)) " +
            "from Item as it " +
            "left join fetch it.comments where it.id = ?1 ")
    Optional<ItemDtoResponse> getItemById(Long itemId);

    @Query("select new ru.practicum.shareit.item.dto.ItemDtoResponse(it" +
            //last booking
            ",(select MIN(it.start) from Booking as it " +
            " join it.item as i " +
            " where i.id = ?1 " +
            " and it.end < CURRENT_TIMESTAMP), " +
            //next booking
            "(select MIN(it.start) from Booking as it " +
            "join it.item as i " +
            "where i.id = ?1 " +
            "and it.start > CURRENT_TIMESTAMP)) " +
            "from Item as it " +
            "left join fetch it.comments where it.owner = ?1 ")
    List<ItemDtoResponse> getUserItems(Long userId);


}
