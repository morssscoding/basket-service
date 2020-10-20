package com.gofluent.basket.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofluent.basket.api.controllers.BasketController;
import com.gofluent.basket.api.dto.CreateBasketDto;
import com.gofluent.basket.api.dto.UpdateBasketDto;
import com.gofluent.basket.core.Basket;
import com.gofluent.basket.core.BasketService;
import com.gofluent.basket.core.Item;
import com.gofluent.basket.core.UpdateBasketAction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class BasketControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private BasketService basketService;

    private BasketController basketController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup(){
        basketController = new BasketController(basketService);
        mockMvc = MockMvcBuilders.standaloneSetup(basketController).build();
    }
    @Test
    public void testCreateBasketEndpoint() throws Exception{
        when(basketService.createBasket(anyString())).thenReturn("test");

        CreateBasketDto request = new CreateBasketDto("user");

        mockMvc.perform(post("/baskets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(
                        status().isCreated()
                );
    }

    @Test
    public void testUpdateBasketEndpoint() throws Exception{
        UpdateBasketDto request = new UpdateBasketDto();
        request.setBasketId("test");
        request.setAction(UpdateBasketAction.ADD);
        request.setItem(new Item());

        mockMvc.perform(put("/baskets")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isOk()
                );
    }

    @Test
    public void testViewBasketEndpoint() throws Exception{
        when(basketService.viewBasket(anyString())).thenReturn(new Basket());
        when(basketService.computeItems(anyList())).thenReturn(BigDecimal.ONE);
        mockMvc.perform(get("/baskets/{0}", "1"))
                .andExpect(
                        status().isOk()
                );
    }
}
