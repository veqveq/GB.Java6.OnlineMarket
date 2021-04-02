package com.veqveq.onlinemarket.controllers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;
import java.util.logging.Logger;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(username = "Test", roles = "TEST")
public class CartControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void createCartTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/v1/cart")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andReturn();
        String cartId = result.getResponse().getContentAsString().replace("\"", "");
        UUID uuid = UUID.fromString(cartId);
        Assertions.assertNotNull(uuid);
    }

    @Test
    public void getCartTest() throws Exception {
        String cartId = mvc.perform(post("/api/v1/cart"))
                .andReturn().getResponse().getContentAsString().replace("\"", "");

        mvc.perform(post("/api/v1/cart/get")
                .param("cartId", cartId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartItems").isArray())
                .andExpect(jsonPath("$.cartItems", hasSize(0)))
                .andExpect(jsonPath("$.cartPrice").isNumber())
                .andReturn();
    }

    @Test
    public void addOrIncItemTest() throws Exception {
        String cartId = mvc.perform(post("/api/v1/cart"))
                .andReturn().getResponse().getContentAsString().replace("\"", "");

        mvc.perform(post("/api/v1/cart/add")
                .param("cartId", cartId)
                .param("productId", "1"))
                .andExpect(status().isOk());

        mvc.perform(post("/api/v1/cart/add")
                .param("cartId", cartId)
                .param("productId", "4"))
                .andExpect(status().isNotFound());

        mvc.perform(post("/api/v1/cart/get")
                .param("cartId", cartId))
                .andExpect(jsonPath("$.cartItems", hasSize(1)))
                .andExpect(jsonPath("$.cartPrice").isNumber());

        mvc.perform(post("/api/v1/cart/add")
                .param("cartId", cartId)
                .param("productId", "1"))
                .andExpect(status().isOk());

        mvc.perform(post("/api/v1/cart/get")
                .param("cartId", cartId))
                .andExpect(jsonPath("$.cartItems", hasSize(1)))
                .andExpect(jsonPath("$.cartPrice").isNumber());
    }

    @Test
    public void removeItemTest() throws Exception {
        String cartId = mvc.perform(post("/api/v1/cart"))
                .andReturn().getResponse().getContentAsString().replace("\"", "");

        mvc.perform(post("/api/v1/cart/add")
                .param("cartId", cartId)
                .param("productId", "1"))
                .andExpect(status().isOk());

        mvc.perform(post("/api/v1/cart/add")
                .param("cartId", cartId)
                .param("productId", "2"))
                .andExpect(status().isOk());

        mvc.perform(post("/api/v1/cart/add")
                .param("cartId", cartId)
                .param("productId", "3"))
                .andExpect(status().isOk());

        mvc.perform(post("/api/v1/cart/del")
                .param("cartId", cartId)
                .param("productId", "2"))
                .andExpect(status().isOk());

        mvc.perform(post("/api/v1/cart/get")
                .param("cartId", cartId))
                .andExpect(jsonPath("$.cartItems", hasSize(2)))
                .andExpect(jsonPath("$.cartPrice").isNumber());
    }

    @Test
    public void decItemTest() throws Exception {
        String cartId = mvc.perform(post("/api/v1/cart"))
                .andReturn().getResponse().getContentAsString().replace("\"", "");

        for (int i = 0; i < 3; i++) {
            mvc.perform(post("/api/v1/cart/add")
                    .param("cartId", cartId)
                    .param("productId", "1"))
                    .andExpect(status().isOk());
        }

        for (int i = 0; i < 2; i++) {
            mvc.perform(post("/api/v1/cart/dec")
                    .param("cartId", cartId)
                    .param("productId", "1"))
                    .andExpect(status().isOk());
        }

        mvc.perform(post("/api/v1/cart/get")
                .param("cartId", cartId))
                .andExpect(jsonPath("$.cartItems", hasSize(1)))
                .andExpect(jsonPath("$.cartPrice").isNumber());

        mvc.perform(post("/api/v1/cart/dec")
                .param("cartId", cartId)
                .param("productId", "1"))
                .andExpect(status().isOk());

        mvc.perform(post("/api/v1/cart/get")
                .param("cartId", cartId))
                .andExpect(jsonPath("$.cartItems", hasSize(0)));
    }

    @Test
    public void cleanCartTest() throws Exception {
        String cartId = mvc.perform(post("/api/v1/cart"))
                .andReturn().getResponse().getContentAsString().replace("\"", "");

        mvc.perform(post("/api/v1/cart/add")
                .param("cartId", cartId)
                .param("productId", "1"))
                .andExpect(status().isOk());

        mvc.perform(post("/api/v1/cart/add")
                .param("cartId", cartId)
                .param("productId", "2"))
                .andExpect(status().isOk());

        mvc.perform(post("/api/v1/cart/add")
                .param("cartId", cartId)
                .param("productId", "3"))
                .andExpect(status().isOk());

        mvc.perform(post("/api/v1/cart/clean")
                .param("cartId", cartId))
                .andExpect(status().isOk());

        mvc.perform(post("/api/v1/cart/get")
                .param("cartId", cartId))
                .andExpect(jsonPath("$.cartItems", hasSize(0)));
    }
}
