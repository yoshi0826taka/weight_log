package com.example.weight_Log.controller;

import com.example.weight_log.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void AddUserTest() throws Exception {
        User user = new User();
        user.setMyouji("山田");
        user.setNamae("太郎");
        user.setMyouji_kana("ヤマダ");
        user.setNamae_kana("タロウ");
        user.setBirth_year(1990);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void UserUpdateTest() throws Exception {
        User user = new User();
        user.setMyouji("鈴木");
        user.setNamae("次郎");
        user.setMyouji_kana("スズキ");
        user.setNamae_kana("ジロウ");
        user.setBirth_day(1995);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void DeleteUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1"))
                .andExpect(status().isOk());
    }
}
