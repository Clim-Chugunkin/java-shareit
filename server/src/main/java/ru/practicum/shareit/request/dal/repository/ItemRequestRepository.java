package ru.practicum.shareit.request.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    @Query("select it from ItemRequest as it " +
            "left join fetch it.answers where it.id = ?1")
    public ItemRequest getItemRequestById(Long id);

    @Query("select it from ItemRequest as it " +
            "left join fetch it.answers where it.requestor = ?1")
    public List<ItemRequest> getUserItemRequests(Long id);
}
