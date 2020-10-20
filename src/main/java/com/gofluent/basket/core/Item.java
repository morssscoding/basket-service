package com.gofluent.basket.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private String itemId;
    private String name;
    private BigDecimal price;
    private long quantity;
}
