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

    private static final Logger logger = Logger.getLogger("");

    @Test
    public void createCartTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/v1/cart")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andReturn();
        String cartId = result.getResponse().getContentAsString().replace("\"", "");
        UUID uuid = UUID.fromString(cartId);
        Assertions.assertNotNull(uuid);
        logger.info(uuid.toString());
    }

    @Test
    public void getCartTest() throws Exception {
        String cartId = mvc.perform(get("/api/v1/cart"))
                .andReturn().getResponse().getContentAsString();

        mvc.perform(post("/api/v1/cart/get")
                .param("uuid", cartId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartItems").isArray())
                .andExpect(jsonPath("$.cartItems", hasSize(0)))
                .andExpect(jsonPath("$.cartPrice").isNumber())
                .andReturn();
    }

    @Test
    public void addToCartTest() throws Exception {
        String cartId = mvc.perform(get("/api/v1/cart"))
                .andReturn().getResponse().getContentAsString();

        mvc.perform(post("/api/v1/cart/add/1")
                .param("uuid", cartId))
                .andExpect(status().isOk());

        mvc.perform(post("/api/v1/cart/add/4")
                .param("uuid", cartId))
                .andExpect(status().isNotFound());

        MvcResult result = mvc.perform(post("/api/v1/cart/get")
                .param("uuid", cartId))
//                .andExpect(jsonPath("$.cartItems", hasSize(1)))
                .andExpect(jsonPath("$.cartPrice").isNumber())
                .andReturn();
//        Integer cartPrice = Integer.parseInt(jsonPath("$.cartPrice",result));
        logger.info(result.getResponse().getContentAsString());
    }
}
