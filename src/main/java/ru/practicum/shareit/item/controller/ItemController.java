package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dal.mapper.ItemDTOMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/{itemId}")
    public ItemDtoResponse getItemById(@PathVariable long itemId) {
        return itemService.getItemById(itemId);
    }

    @GetMapping
    public List<ItemDtoResponse> getUserAllItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getUserAllItems(userId);
    }

    @PostMapping
    public ItemDtoResponse addItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                                   @Valid @RequestBody ItemDto itemDto) {
        Item item = ItemDTOMapper.toItem(itemDto, userId, null);
        return ItemDTOMapper.toItemDtoResponse(itemService.addItem(item));
    }

    @PatchMapping("/{itemId}")
    public ItemDtoResponse update(@RequestHeader("X-Sharer-User-Id") Long userId,
                                  @PathVariable Long itemId,
                                  @RequestBody ItemDto itemDto) {
        Item item = ItemDTOMapper.toItem(itemDto, userId, itemId);
        return ItemDTOMapper.toItemDtoResponse(itemService.updateItem(item));
    }

    @GetMapping("/search")
    public List<ItemDtoResponse> search(@RequestParam String text) {
        if (text.isEmpty()) return new ArrayList<>();
        return itemService.search(text).stream()
                .map(ItemDTOMapper::toItemDtoResponse)
                .toList();
    }

    @PostMapping("/{itemId}/comment")
    public Comment addComment(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @PathVariable(name = "itemId") Long itemId,
                              @RequestBody Comment comment) {
        comment.setItemId(itemId);
        return itemService.addComment(userId, comment);
    }
}
