package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserServiceImpl;
import ru.practicum.shareit.user.service.UserServiceImplTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRequestServiceImplTest {

    private final UserServiceImpl userService;
    private final ItemRequestServiceImpl itemRequestService;

    @Test
    public void addAndGetRequestTest() {
        User user = UserServiceImplTest.generateTestUser();
        Long userId = userService.addUser(user).getId();
        ItemRequestDto request = new ItemRequestDto();
        request.setDescription("new request");
        Long requestID = itemRequestService.save(request, userId).getId();

        ItemRequestResponseDto response = itemRequestService.getItemRequestById(requestID);
        assertEquals("new request", response.getDescription());
    }

    @Test
    public void getUserItemRequestsTest() {
        User user = UserServiceImplTest.generateTestUser();
        Long userId = userService.addUser(user).getId();

        ItemRequestDto request = new ItemRequestDto();
        request.setDescription("new request");
        itemRequestService.save(request, userId);

        ItemRequestDto request2 = new ItemRequestDto();
        request2.setDescription("new request2");
        itemRequestService.save(request2, userId);

        List<ItemRequestResponseDto> list = itemRequestService.getUserItemRequests(userId);
        assertEquals(2, list.size());

    }
}