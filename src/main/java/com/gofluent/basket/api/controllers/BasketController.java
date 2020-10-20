package com.gofluent.basket.api.controllers;

import com.gofluent.basket.api.dto.BasketDto;
import com.gofluent.basket.api.dto.CreateBasketDto;
import com.gofluent.basket.api.dto.UpdateBasketDto;
import com.gofluent.basket.core.Basket;
import com.gofluent.basket.core.BasketService;
import com.gofluent.basket.core.exception.CreateBasketException;
import com.gofluent.basket.core.exception.ViewBasketException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/baskets")
@AllArgsConstructor
public class BasketController {

    private BasketService basketService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBasket(@RequestBody @Validated CreateBasketDto request) {
        try {
            String basketId = basketService.createBasket(request.getUser());
            return new ResponseEntity<>(basketId, HttpStatus.CREATED);
        }catch (CreateBasketException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{basketId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> viewBasket(@PathVariable("basketId") String basketId) {
        try {
            Basket basket = basketService.viewBasket(basketId);
            BasketDto basketDto = BasketDto.builder()
                    .basket(basket)
                    .total(basketService.computeItems(basket.getItems()))
                    .build();
            return new ResponseEntity<>(basketDto, HttpStatus.OK);
        }catch (ViewBasketException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateBasket(@RequestBody UpdateBasketDto request) {
        try {
            basketService.updateBasket(request.getBasketId(), request.getItem(), request.getAction());
            return new ResponseEntity<>("Basket Updated!", HttpStatus.OK);
        }catch (ViewBasketException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
