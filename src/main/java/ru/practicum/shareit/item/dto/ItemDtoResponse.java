package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class ItemDtoResponse {
    Long id;
    String name;
    String description;
    Boolean available;
    LocalDateTime lastBooking;
    LocalDateTime nextBooking;
    List<Comment> comments;

    public ItemDtoResponse(Item item, LocalDateTime lastBooking, LocalDateTime nextBooking) {
        id = item.getId();
        name = item.getName();
        description = item.getDescription();
        available = item.getAvailable();
        comments = item.getComments();
        this.lastBooking = lastBooking;
        this.nextBooking = nextBooking;
    }
}
