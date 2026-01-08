package ru.practicum.shareit.item.dal.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoForRequest;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.item.model.Item;

public class ItemDTOMapper {

    public static ItemDto toItemDTO(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();

    }

    public static ItemDtoResponse toItemDtoResponse(Item item) {
        ItemDtoResponse response = new ItemDtoResponse();
        response.setId(item.getId());
        response.setName(item.getName());
        response.setDescription(item.getDescription());
        response.setAvailable(item.getAvailable());
        response.setComments(item.getComments());
        return response;
    }

    public static Item toItem(ItemDto itemDto, Long ownerId, Long itemId) {
        Item item = new Item();
        item.setId((itemId == null) ? itemDto.getId() : itemId);
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setOwner(ownerId);
        if (itemDto.getRequestId() != null) item.setRequest(itemDto.getRequestId());
        return item;
    }

    public static ItemDtoForRequest toRequest(Item item) {
        ItemDtoForRequest itemDtoForRequest = new ItemDtoForRequest();
        itemDtoForRequest.setId(item.getId());
        itemDtoForRequest.setName(item.getName());
        itemDtoForRequest.setOwner(item.getOwner());
        return itemDtoForRequest;
    }

}
