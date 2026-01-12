package ru.practicum.shareit.item;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

@RestController
@RequestMapping(path = "/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemClient itemClient;


    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@PathVariable long itemId) {

        return itemClient.getItemById(itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getUserAllItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.getUserAllItems(userId);
    }

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                                          @Valid @RequestBody ItemDto itemDto) {

        return itemClient.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @PathVariable Long itemId,
                                         @RequestBody ItemDto itemDto) {

        return itemClient.update(userId, itemId, itemDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam String text) {
        return itemClient.search(text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader("X-Sharer-User-Id") Long userId,
                                             @PathVariable(name = "itemId") Long itemId,
                                             @Valid @RequestBody CommentDto comment) {

        return itemClient.addComment(userId, itemId, comment);
    }

}
