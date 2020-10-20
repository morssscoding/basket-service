package com.gofluent.basket.database.repo;

import com.gofluent.basket.database.entity.BasketItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketItemEntityRepository extends JpaRepository<BasketItemEntity, Long> {
    List<BasketItemEntity> findByBasketId(String basketId);
    Optional<BasketItemEntity> findByItemId(String itemId);
}
