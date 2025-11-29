package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dal.repository.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dal.repository.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public List<Item> getUserAllItems(Long userId) {
        return itemRepository.getUserAllItems(userId);
    }

    public Item getItemById(Long id) {
        return itemRepository.getItemById(id);
    }

    public Item addItem(Item newItem) {
        User user = userRepository.getUserById(newItem.getOwner());
        Item item = itemRepository.addItem(newItem);
        log.info("Пользователь {} добавил(а) новую вещь {}", user.getName(), item.getName());
        return item;
    }

    public Item updateItem(Item item) {
        userRepository.getUserById(item.getOwner());
        return itemRepository.update(item);
    }

    public void removeItem(Long id) {
        Item item = itemRepository.getItemById(id);
        itemRepository.itemRemove(id);
        log.info("Вещь {} удален", item.getName());
    }

    public List<Item> search(String text) {
        return itemRepository.search(text);
    }
}
