package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserServiceImpl;
import ru.practicum.shareit.user.service.UserServiceImplTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemServiceImplTest {

    private final ItemServiceImpl itemService;
    private final UserServiceImpl userService;

    @Test
    public void getUsersAllItemsTest() {
        User user = UserServiceImplTest.generateTestUser();
        Long userId = userService.addUser(user).getId();
        Item item = generateItem(userId);
        itemService.addItem(item);
        Item item2 = generateItem(userId);
        item2.setName("name2");
        item2.setDescription("description2");
        itemService.addItem(item2);
        List<ItemDtoResponse> list = itemService.getUserAllItems(userId);
        assertEquals(2, list.size());
    }

    @Test
    public void addItemAndGetItemByIdTest() {
        User user = UserServiceImplTest.generateTestUser();
        Long userId = userService.addUser(user).getId();
        Item item = generateItem(userId);
        Long itemId = itemService.addItem(item).getId();
        ItemDtoResponse newItem = itemService.getItemById(itemId);
        assertEquals(item.getName(), newItem.getName(), "wrong name");
        assertEquals(item.getAvailable(), newItem.getAvailable(), "wrong availability");
        assertEquals(item.getDescription(), newItem.getDescription(), "wrong description");
    }

    @Test
    public void updateItemTest() {
        User user = UserServiceImplTest.generateTestUser();
        Long userId = userService.addUser(user).getId();
        Item item = generateItem(userId);
        Long itemId = itemService.addItem(item).getId();
        Item newItem = new Item();
        newItem.setId(itemId);
        newItem.setOwner(userId);
        newItem.setDescription("newDescription");
        itemService.updateItem(newItem);

        ItemDtoResponse itemForTest = itemService.getItemById(itemId);
        assertEquals("newDescription", itemForTest.getDescription(), "wrong description");

    }

    @Test
    public void searchTest() {
        User user = UserServiceImplTest.generateTestUser();
        Long userId = userService.addUser(user).getId();
        Item item = generateItem(userId);
        Long itemId = itemService.addItem(item).getId();
        List<Item> list = itemService.search("something");
        assertEquals(0, list.size());
        list = itemService.search("na");
        assertEquals(1, list.size());
    }

    public static Item generateItem(Long userId) {
        Item item = new Item();
        item.setName("name");
        item.setDescription("description");
        item.setAvailable(true);
        item.setOwner(userId);
        return item;
    }
}