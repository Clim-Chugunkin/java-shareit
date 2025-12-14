package ru.practicum.shareit.item.dal.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> getUserAllItems(Long userId);

    Item addItem(Item item);

    Item update(Item item);

    Item getItemById(long id);

    void itemRemove(long id);

    List<Item> search(String text);
}
