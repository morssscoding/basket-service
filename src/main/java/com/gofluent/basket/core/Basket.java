package com.gofluent.basket.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Basket {

    private String user;
    private String basketId;
    private Status status;
    private List<Item> items;

    public enum  Status {
        ACTIVE,
        DELETED,
        CHECKED_OUT,
    }
}
