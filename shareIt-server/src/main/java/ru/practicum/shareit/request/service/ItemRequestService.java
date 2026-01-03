package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dal.repository.ItemRequestRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;


public interface ItemRequestService {
    ItemRequestResponseDto save(ItemRequestDto itemRequestDto, Long userId);
    ItemRequestResponseDto getItemRequestById(Long id);
    List<ItemRequestResponseDto> getUserItemRequests(Long userId);
}
