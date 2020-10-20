package com.gofluent.basket.api.dto;

import com.gofluent.basket.core.Item;
import com.gofluent.basket.core.UpdateBasketAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBasketDto {

    @NotEmpty
    private String basketId;
    @NonNull
    private Item item;
    @NotNull
    private UpdateBasketAction action;
}
