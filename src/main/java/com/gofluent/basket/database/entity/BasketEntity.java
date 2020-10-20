package com.gofluent.basket.database.entity;

import com.gofluent.basket.core.Basket;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "BASKETS")
public class BasketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "USER")
    private String user;

    @Column(name = "BASKET_UUID", unique = true)
    private String basketId;

    @Column(name = "STATUS")
    private Basket.Status status;
}
