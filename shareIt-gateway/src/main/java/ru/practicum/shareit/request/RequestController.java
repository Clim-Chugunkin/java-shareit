package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> save(@RequestHeader("X-Sharer-User-Id") Long userId,
                                       @RequestBody ItemRequestDto itemRequestDto) {
        return requestClient.save(itemRequestDto, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUserItemRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestClient.getUserItemRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequestById(@PathVariable(name = "requestId") Long requestId) {
        return requestClient.getItemRequestById(requestId);
    }
}
