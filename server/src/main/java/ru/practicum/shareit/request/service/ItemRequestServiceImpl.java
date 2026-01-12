package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConditionsNotMetException;
import ru.practicum.shareit.request.dal.mapper.ItemRequestDtoMapper;
import ru.practicum.shareit.request.dal.repository.ItemRequestRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dal.repository.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;

    @Override
    public ItemRequestResponseDto save(ItemRequestDto itemRequestDto, Long userId) {
        //check if user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ConditionsNotMetException("Такого пользователя нет"));
        //create itemRequest from itemRequestDto
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(itemRequestDto.getDescription());
        itemRequest.setRequestor(userId);
        itemRequest.setCreated(LocalDateTime.now());
        return ItemRequestDtoMapper.toResponse(itemRequestRepository.save(itemRequest));
    }

    @Override
    public ItemRequestResponseDto getItemRequestById(Long id) {
        return ItemRequestDtoMapper.toResponse(itemRequestRepository.getItemRequestById(id));
    }

    @Override
    public List<ItemRequestResponseDto> getUserItemRequests(Long userId) {
        return itemRequestRepository.getUserItemRequests(userId).stream()
                .map(ItemRequestDtoMapper::toResponse)
                .toList();
    }
}
