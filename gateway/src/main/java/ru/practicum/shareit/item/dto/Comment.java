package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
public class Comment {
    private Long id;
    private Long itemId;
    private String text;
    private LocalDateTime created;
    private String authorName;
}
