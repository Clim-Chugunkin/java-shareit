package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;

import java.util.List;


public interface ItemRequestService {
    ItemRequestResponseDto save(ItemRequestDto itemRequestDto, Long userId);

    ItemRequestResponseDto getItemRequestById(Long id);

    List<ItemRequestResponseDto> getUserItemRequests(Long userId);
}
