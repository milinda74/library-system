package com.fortunaglobal.library.controller;

import com.fortunaglobal.library.model.Borrower;
import com.fortunaglobal.library.service.BorrowerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BorrowerController.class)
class BorrowerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    //@MockBean
    @MockitoBean
    private BorrowerService borrowerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerBorrower_Success() throws Exception {
        Borrower borrower = new Borrower("John Doe", "john@example.com");
        given(borrowerService.registerBorrower(any())).willReturn(borrower);

        mockMvc.perform(post("/api/borrowers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(borrower)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void getBorrowerById_Success() throws Exception {
        Borrower borrower = new Borrower(1L, "John Doe", "john@example.com");
        given(borrowerService.getBorrowerById(1L)).willReturn(borrower);

        mockMvc.perform(get("/api/borrowers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateBorrower_Success() throws Exception {
        Borrower existing = new Borrower(1L, "John Doe", "john@example.com");
        Borrower updated = new Borrower(1L, "John Updated", "john.new@example.com");

        given(borrowerService.updateBorrower(anyLong(), any())).willReturn(updated);

        mockMvc.perform(put("/api/borrowers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"));
    }
}