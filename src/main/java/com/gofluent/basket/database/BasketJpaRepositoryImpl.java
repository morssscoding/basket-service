package com.gofluent.basket.database;

import com.gofluent.basket.core.Basket;
import com.gofluent.basket.core.Item;
import com.gofluent.basket.database.entity.BasketEntity;
import com.gofluent.basket.database.entity.BasketItemEntity;
import com.gofluent.basket.database.repo.BasketEntityRepository;
import com.gofluent.basket.database.repo.BasketItemEntityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Repository
@Transactional
@AllArgsConstructor
public class BasketJpaRepositoryImpl implements BasketRepository {

    private BasketEntityRepository basketRepository;
    private BasketItemEntityRepository basketItemEntityRepository;

    @Override
    public String create(String user) {
        String basketId = UUID.randomUUID().toString();
        BasketEntity basketEntity = new BasketEntity();
        basketEntity.setBasketId(basketId);
        basketEntity.setUser(user);
        basketEntity.setStatus(Basket.Status.ACTIVE);
        basketRepository.save(basketEntity);
        log.info("[CREATED] Basket Id {} created.", basketId);
        return basketId;
    }

    @Override
    public boolean hasActiveBasket(String user) {
        return basketRepository.countByUserAndStatus(user, Basket.Status.ACTIVE) > 0;
    }

    @Override
    public Optional<Basket> findIfExists(String basketId) {
        Optional<BasketEntity> basketEntity = basketRepository.findByBasketId(basketId);
        return basketEntity.map(this::fromEntity);
    }

    @Override
    public List<Item> getItems(String basketId) {
        return basketItemEntityRepository.findByBasketId(basketId).stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void addItem(String basketId, Item item) {
        BasketItemEntity basketItemEntity = new BasketItemEntity();
        basketItemEntity.setBasketId(basketId);
        basketItemEntity.setItemId(item.getItemId());
        basketItemEntity.setName(item.getName());
        basketItemEntity.setPrice(item.getPrice());
        basketItemEntity.setQuantity(item.getQuantity());
        basketItemEntityRepository.save(basketItemEntity);
        log.info("[ADDED] Item {} added to basket {}.", item.getItemId(), basketId);
    }

    @Override
    public void removeItem(String basketId, Item item) {
        Optional<BasketItemEntity> basketItemEntity = basketItemEntityRepository.findByItemId(item.getItemId());
        if (basketItemEntity.isPresent()){
            basketItemEntityRepository.deleteById(basketItemEntity.get().getId());
        } else {
            log.warn("No item deleted because it does not exist in the basket");
        }
    }

    private Item fromEntity(BasketItemEntity entity){
        return Item.builder()
                .itemId(entity.getItemId())
                .name(entity.getName())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .build();
    }
    private Basket fromEntity(BasketEntity basketEntity){
        return Basket.builder()
                .basketId(basketEntity.getBasketId())
                .user(basketEntity.getUser())
                .status(basketEntity.getStatus())
                .build();
    }
}
