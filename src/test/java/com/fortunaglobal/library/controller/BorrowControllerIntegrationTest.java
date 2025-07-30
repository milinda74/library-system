package com.fortunaglobal.library.controller;

import com.fortunaglobal.library.model.BorrowRecord;
import com.fortunaglobal.library.service.BorrowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BorrowController.class)
class BorrowControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BorrowService borrowService;

    @Test
    void borrowBook_Success() throws Exception {
        BorrowRecord record = new BorrowRecord();
        given(borrowService.borrowBook(anyLong(), anyLong())).willReturn(record);

        mockMvc.perform(post("/api/borrow")
                        .param("bookId", "1")
                        .param("borrowerId", "1"))
                .andExpect(status().isCreated());
    }

    @Test
    void returnBook_Success() throws Exception {
        BorrowRecord record = new BorrowRecord();
        record.setReturnDate(LocalDateTime.now());
        given(borrowService.returnBook(anyLong(), anyLong())).willReturn(record);

        mockMvc.perform(post("/api/borrow/return")
                        .param("bookId", "1")
                        .param("borrowerId", "1"))
                .andExpect(status().isOk());
    }
}
