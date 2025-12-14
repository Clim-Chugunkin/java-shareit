package ru.practicum.shareit.item.dal.repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public List<Item> getUserAllItems(Long userId) {
        return items.values().stream()
                .filter(item -> item.getOwner() == userId)
                .toList();
    }

    @Override
    public Item addItem(Item item) {
        Item newItem = item.toBuilder()
                .id(getNextId())
                .build();
        items.put(newItem.getId(), newItem);
        return newItem;
    }

    @Override
    public Item update(Item item) {
        Item oldItem = getItemById(item.getId());
        Item itemUpdated = oldItem.toBuilder()
                .name((item.getName() != null) ? item.getName() : oldItem.getName())
                .description((item.getDescription() != null) ? item.getDescription() : oldItem.getDescription())
                .available((item.getAvailable() != null) ? item.getAvailable() : oldItem.getAvailable())
                .build();
        items.put(itemUpdated.getId(), itemUpdated);
        return itemUpdated;
    }

    @Override
    public Item getItemById(long id) {
        return items.get(id);
    }

    @Override
    public void itemRemove(long id) {
        items.remove(id);
    }

    @Override
    public List<Item> search(String text) {
        String searchText = text.toUpperCase();
        return items.values().stream()
                .filter(Item::isAvailable)
                .filter(item -> item.getName().toUpperCase().contains(searchText) || item.getDescription().toUpperCase().contains(searchText))
                .toList();
    }

    private long getNextId() {
        long currentMaxId = items.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
