package com.veqveq.onlinemarket.controllers;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(username = "Test", roles = "TEST")
public class CartControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void createCartTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/v1/cart")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andReturn();
        String cartId = result.getResponse().getContentAsString();
        Assertions.assertNotNull(cartId);
    }

    @Test
    public void addToCartTest() throws Exception {
        MvcResult uuidReqResult = mvc.perform(get("/api/v1/cart"))
                .andReturn();

        String cartId = uuidReqResult.getResponse().getContentAsString();
//        cartId = cartId.replace("\"","");

        MvcResult result = mvc.perform(post("/api/v1/cart/add/1")
                .param("uuid",cartId))
//                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }


}
