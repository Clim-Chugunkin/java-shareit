package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dal.mapper.ItemDTOMapper;
import ru.practicum.shareit.item.dto.ItemDto;
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
    public ItemDto getItemById(@PathVariable long itemId) {
        return itemService.getItemById(itemId);
    }

    @GetMapping
    public List<ItemDto> getUserAllItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getUserAllItems(userId);
    }

    @PostMapping
    public ItemDto addItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                           @Valid @RequestBody ItemDto itemDto) {
        Item item = ItemDTOMapper.toItem(itemDto);
        item.setOwner(userId);
        return ItemDTOMapper.toItemDTO(itemService.addItem(item));
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @PathVariable Long itemId,
                          @RequestBody ItemDto itemDto) {
        Item item = ItemDTOMapper.toItem(itemDto);
        item.setId(itemId);
        item.setOwner(userId);
        return ItemDTOMapper.toItemDTO(itemService.updateItem(item));
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam String text) {
        if (text.isEmpty()) return new ArrayList<>();
        return itemService.search(text).stream()
                .map(ItemDTOMapper::toItemDTO)
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
