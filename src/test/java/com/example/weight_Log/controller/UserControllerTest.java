package com.example.weight_log.controller;

import com.example.weight_log.model.User;
import com.example.weight_log.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private Long testUserId;

    @BeforeEach
    void setup() {
        userRepository.deleteAll(); // 前のデータをリセット
        User user = new User();
        user.setMyouji("TestMyouji");
        user.setNamae("TestNamae");
        user.setBirth_year(1990);
        testUserId = userRepository.save(user).getId();
    }

    @Test
    void testAddUser() throws Exception {
        String json = "{\"name\":\"Yamada\",\"birthYear\":1985,\"gender\":\"male\"}";
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Yamada"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test User"));
    }

    @Test
    void testUpdateUser() throws Exception {
        String json = "{\"name\":\"Updated User\",\"birthYear\":1995,\"gender\":\"female\"}";
        mockMvc.perform(put("/api/users/" + testUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated User"));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/" + testUserId))
                .andExpect(status().isOk());
    }
}
