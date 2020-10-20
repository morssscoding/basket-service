package com.gofluent.basket.core;

import com.gofluent.basket.core.exception.CreateBasketException;
import com.gofluent.basket.core.exception.ViewBasketException;
import com.gofluent.basket.database.BasketRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class BasketServiceImpl implements BasketService {

    private BasketRepository basketRepository;

    @Override
    public String createBasket(String user) {
        log.error("[CREATED] User={}", user);
        if (basketRepository.hasActiveBasket(user)){
            String message = String.format("User [%s] has already an active basket.", user);
            log.error("[FAILED] {}", message);
            throw new CreateBasketException(message);
        }
        return basketRepository.create(user);
    }

    @Override
    public Basket viewBasket(String basketId) {
        Optional<Basket> optionalBasket = basketRepository.findIfExists(basketId);
        if (optionalBasket.isPresent()){
            Basket basket = optionalBasket.get();
            List<Item> items = basketRepository.getItems(basketId);
            basket.setItems(items);
            return basket;
        } else {
            String message = String.format("Basket %s not found.", basketId);
            log.error("[FAILED] = {}", message);
            throw new ViewBasketException(message);
        }
    }

    @Override
    public void updateBasket(String basketId, Item item, UpdateBasketAction action) {
        Optional<Basket> basket = basketRepository.findIfExists(basketId);
        if (!basket.isPresent()){
            String message = String.format("Basket %s not found.", basketId);
            log.error("[FAILED] = {}", message);
            throw new ViewBasketException(message);
        }
        if (UpdateBasketAction.ADD.equals(action)){
            basketRepository.addItem(basketId, item);
        } else if (UpdateBasketAction.REMOVE.equals(action)){
            basketRepository.removeItem(basketId, item);
        } else {
            throw new IllegalArgumentException("Unsupported update basket action.");
        }
    }

    @Override
    public BigDecimal computeItems(List<Item> items){
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
