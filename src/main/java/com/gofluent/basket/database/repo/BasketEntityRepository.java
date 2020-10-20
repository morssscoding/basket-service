package com.gofluent.basket.database.repo;

import com.gofluent.basket.core.Basket;
import com.gofluent.basket.database.entity.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketEntityRepository extends JpaRepository<BasketEntity, Long> {
    Long countByUserAndStatus(String user, Basket.Status status);
    Optional<BasketEntity> findByBasketId(String basketId);
}
