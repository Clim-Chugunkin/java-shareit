package ru.practicum.shareit.item.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOwner(Long ownerId);

    List<Item> findByNameContainingIgnoreCaseAndAvailable(String text, Boolean available);
}
