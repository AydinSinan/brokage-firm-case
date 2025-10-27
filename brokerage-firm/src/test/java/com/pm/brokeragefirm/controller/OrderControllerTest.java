package com.pm.brokeragefirm.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    @WithMockUser(username = "alice", roles = {"CUSTOMER"}) // <-- Mock User eklendi
    void createOrder_201() throws Exception {
        mvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON)
                        // .header("Authorization", basic("alice","alice123")) <-- Bu satır artık GEREKSİZ
                        .content("{\"assetName\":\"BTC\",\"side\":\"BUY\",\"size\":10,\"price\":100}"))
                .andExpect(status().isCreated());
    }
}