package ru.practicum.shareit.request.dal.mapper;

import ru.practicum.shareit.item.dal.mapper.ItemDTOMapper;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.request.model.ItemRequest;

public class ItemRequestDtoMapper {
    public static ItemRequestResponseDto toResponse(ItemRequest itemRequest) {
        ItemRequestResponseDto response = new ItemRequestResponseDto();
        response.setId(itemRequest.getId());
        response.setDescription(itemRequest.getDescription());
        response.setCreated(itemRequest.getCreated());


        response.setItems((itemRequest.getAnswers() == null) ? null : itemRequest.getAnswers().stream()
                .map(ItemDTOMapper::toRequest)
                .toList());
        return response;
    }
}
