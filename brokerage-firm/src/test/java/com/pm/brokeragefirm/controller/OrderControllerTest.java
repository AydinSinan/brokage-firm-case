package com.pm.brokeragefirm.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    MockMvc mvc;

    private String basic(String u, String p) { return "Basic " + Base64.getEncoder().encodeToString((u+":"+p).getBytes()); }

    @Test
    void createOrder_201() throws Exception {
        mvc.perform(post("/api/orders").contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .header("Authorization", basic("alice","alice123"))
                        .content("{\"assetName\":\"TRY\",\"side\":\"BUY\",\"size\":10,\"price\":100}"))
                .andExpect(status().isCreated());
    }
}