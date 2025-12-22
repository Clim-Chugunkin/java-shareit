package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments", schema = "public")
@Getter
@Setter
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "comment")
    private String text;
    private LocalDateTime created;
    @Transient
    private String authorName;
}
