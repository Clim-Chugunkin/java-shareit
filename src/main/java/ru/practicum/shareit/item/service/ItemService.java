package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    List<ItemDto> getUserAllItems(Long userId);

    ItemDto getItemById(Long id);

    Item addItem(Item newItem);

    Item updateItem(Item item);

    List<Item> search(String text);

    Comment addComment(Long userId, Comment comment);
}
