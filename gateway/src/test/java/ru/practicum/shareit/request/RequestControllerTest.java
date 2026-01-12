package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RequestControllerTest {

    @Mock
    private RequestClient requestClient;

    @InjectMocks
    private RequestController requestController;

    private final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mvc;

    private ItemRequestDto itemRequestDto;
    private List<ItemRequestDto> requests;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(requestController)
                .build();

        itemRequestDto = new ItemRequestDto("item description");
        requests = new ArrayList<>();
        requests.add(itemRequestDto);
        requests.add(new ItemRequestDto("item2 description"));
    }

    @Test
    public void addNewRequestTest() throws Exception {
        when(requestClient.save(any(), any()))
                .thenReturn(ResponseEntity.ok(itemRequestDto));


        mvc.perform(post("/requests")
                        .content(mapper.writeValueAsString(itemRequestDto))
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(itemRequestDto.getDescription()));
    }

    @Test
    public void getItemRequestByIdTest() throws Exception {
        when(requestClient.getItemRequestById(any()))
                .thenReturn(ResponseEntity.ok(itemRequestDto));

        Long requestId = 1L;

        mvc.perform(get("/requests/" + requestId)
                        .content(mapper.writeValueAsString(itemRequestDto))
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(itemRequestDto.getDescription()));
        verify(requestClient, times(1))
                .getItemRequestById(requestId);
    }

    @Test
    public void getUserItemRequestsTest() throws Exception {
        when(requestClient.getUserItemRequests(any()))
                .thenReturn(ResponseEntity.ok(requests));

        Long userId = 1L;

        mvc.perform(get("/requests")
                        .content(mapper.writeValueAsString(itemRequestDto))
                        .header("X-Sharer-User-Id", userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        verify(requestClient, times(1))
                .getUserItemRequests(userId);
    }

}