package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestResponseDto save(@RequestHeader("X-Sharer-User-Id") Long userId,
                                       @RequestBody ItemRequestDto itemRequestDto) {
        return itemRequestService.save(itemRequestDto, userId);
    }

    @GetMapping
    public List<ItemRequestResponseDto> getUserItemRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.getUserItemRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestResponseDto getItemRequestById(@PathVariable(name = "requestId") Long requestId) {
        return itemRequestService.getItemRequestById(requestId);
    }

}
