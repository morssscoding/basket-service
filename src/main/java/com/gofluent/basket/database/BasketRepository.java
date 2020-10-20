package com.gofluent.basket.database;

import com.gofluent.basket.core.Basket;
import com.gofluent.basket.core.Item;

import java.util.List;
import java.util.Optional;

public interface BasketRepository {
    String create(String user);
    boolean hasActiveBasket(String user);
    Optional<Basket> findIfExists(String basketId);
    List<Item> getItems(String basketId);
    void addItem(String basketId, Item item);
    void removeItem(String basketId, Item item);
}
