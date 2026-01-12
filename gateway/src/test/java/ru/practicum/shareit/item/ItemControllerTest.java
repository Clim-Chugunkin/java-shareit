package ru.practicum.shareit.item;

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
import ru.practicum.shareit.item.dto.ItemDto;

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
class ItemControllerTest {

    @Mock
    private ItemClient itemClient;

    @InjectMocks
    private ItemController itemController;

    private final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mvc;

    private ItemDto itemDto;
    private List<ItemDto> items;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(itemController)
                .build();

        itemDto = new ItemDto(1L, "itemName", "itemDescription", true, null);
        items = new ArrayList<>();
        items.add(itemDto);
        items.add(new ItemDto(2L, "itemName2", "itemDescription2", true, null));
    }

    @Test
    public void addNewItemTest() throws Exception {
        when(itemClient.addItem(any(), any()))
                .thenReturn(ResponseEntity.ok(itemDto));


        mvc.perform(post("/items")
                        .content(mapper.writeValueAsString(itemDto))
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("itemName"));
    }

    @Test
    public void getItemByIdTest() throws Exception {
        when(itemClient.getItemById(any()))
                .thenReturn(ResponseEntity.ok(itemDto));


        mvc.perform(get("/items/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("itemName"));
    }

    @Test
    public void getUserAllItemsTest() throws Exception {
        when(itemClient.getUserAllItems(any()))
                .thenReturn(ResponseEntity.ok(items));

        mvc.perform(get("/items")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").value("itemName"));

    }

    @Test
    public void searchTest() throws Exception {
        when(itemClient.search(any()))
                .thenReturn(ResponseEntity.ok(items));

        mvc.perform(get("/items/search?text=" + "text")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").value("itemName"));
        verify(itemClient, times(1))
                .search("text");
    }


}