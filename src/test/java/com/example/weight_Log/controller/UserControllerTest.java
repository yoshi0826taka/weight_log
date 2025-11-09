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
        user.setBirth_month(1);
        user.setBirth_day(1);
        user.setEmail("test@example.com");
        user.setPassword("secret");
        testUserId = userRepository.save(user).getId();
    }

    @Test
    void testAddUser() throws Exception {
    String json = "{\"myouji\":\"Yamada\",\"namae\":\"Taro\",\"birth_year\":1985,\"birth_month\":8,\"birth_day\":15,\"email\":\"yamada@example.com\",\"password\":\"password1\"}";
    mockMvc.perform(post("/api/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.myouji").value("Yamada"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].myouji").value("TestMyouji"));
    }

    @Test
    void testUpdateUser() throws Exception {
    String json = "{\"myouji\":\"UpdatedMyouji\",\"namae\":\"UpdatedNamae\",\"birth_year\":1995,\"birth_month\":6,\"birth_day\":20,\"email\":\"updated@example.com\",\"password\":\"newpassword\"}";
    mockMvc.perform(put("/api/users/" + testUserId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.myouji").value("UpdatedMyouji"));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/" + testUserId))
                .andExpect(status().isOk());
    }
}