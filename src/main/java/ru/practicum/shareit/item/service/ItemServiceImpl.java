package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dal.repository.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.ConditionsNotMetException;
import ru.practicum.shareit.exception.InvalidArgumentException;
import ru.practicum.shareit.item.dal.mapper.ItemDTOMapper;
import ru.practicum.shareit.item.dal.repository.CommentRepository;
import ru.practicum.shareit.item.dal.repository.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dal.repository.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;

    @Override
    public List<ItemDto> getUserAllItems(Long userId) {
        return itemRepository.findByOwner(userId).stream()
                .map(item -> ItemDTOMapper.toItemDTO(item, bookingRepository.findLastBooking(item.getId()), bookingRepository.findNextBooking(item.getId()), commentRepository.findByItemId(item.getId())))
                .toList();
    }

    public ItemDto getItemById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ConditionsNotMetException("такой вещи нет"));
        return ItemDTOMapper.toItemDTO(item, null, bookingRepository.findNextBooking(item.getId()), commentRepository.findByItemId(id));
    }

    public Item addItem(Item newItem) {
        User user = userRepository.findById(newItem.getOwner()).orElseThrow(() -> new ConditionsNotMetException("пользователь не наиден"));
        Item item = itemRepository.save(newItem);
        log.info("Пользователь {} добавил(а) новую вещь {}", user.getName(), item.getName());
        return item;
    }

    public Item updateItem(Item item) {
        if (item.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        userRepository.findById(item.getOwner()).orElseThrow(() -> new ConditionsNotMetException("пользователь не найден"));

        Item oldItem = itemRepository.findById(item.getId()).orElseThrow(() -> new ConditionsNotMetException("такой вещи нет"));
        oldItem.setName((item.getName() != null) ? item.getName() : oldItem.getName());
        oldItem.setDescription((item.getDescription() != null) ? item.getDescription() : oldItem.getDescription());
        oldItem.setAvailable((item.getAvailable() != null) ? item.getAvailable() : oldItem.getAvailable());
        return itemRepository.save(oldItem);
    }

    public void removeItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ConditionsNotMetException("такой вещи нет"));
        itemRepository.deleteById(id);
        log.info("Вещь {} удален", item.getName());
    }

    public List<Item> search(String text) {
        return itemRepository.findByNameContainingIgnoreCaseAndAvailable(text, true);
    }

    @Override
    public Comment addComment(Long userId, Comment comment) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ConditionsNotMetException("пользователь не найден"));
        List<Booking> list = bookingRepository.findByBookerIdAndItemId(userId, comment.getItemId());

        if (list.stream().anyMatch(booking -> booking.getEnd().isBefore(LocalDateTime.now()))
        ) {
            comment.setCreated(LocalDateTime.now());
            comment = commentRepository.save(comment);
            comment.setAuthorName(user.getName());
            return comment;
        } else {
            throw new InvalidArgumentException("пользователь не брал вещь");
        }
    }
}
