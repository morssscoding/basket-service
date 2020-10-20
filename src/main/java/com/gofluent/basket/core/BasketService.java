package com.gofluent.basket.core;

import java.math.BigDecimal;
import java.util.List;

public interface BasketService {
    String createBasket(String user);
    Basket viewBasket(String basketId);

    BigDecimal computeItems(List<Item> items);
    void updateBasket(String basketId, Item item, UpdateBasketAction action);
}
