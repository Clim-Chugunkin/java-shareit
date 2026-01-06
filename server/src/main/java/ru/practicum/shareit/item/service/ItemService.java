package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    List<ItemDtoResponse> getUserAllItems(Long userId);

    ItemDtoResponse getItemById(Long id);

    Item addItem(Item newItem);

    Item updateItem(Item item);

    List<Item> search(String text);

    Comment addComment(Long userId, Comment comment);
}
