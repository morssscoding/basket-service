package com.gofluent.basket.api.dto;

import com.gofluent.basket.core.Basket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketDto {
    private Basket basket;
    private BigDecimal total;
}
