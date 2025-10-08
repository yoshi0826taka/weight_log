package com.example.weight_log.controller;

import com.example.weight_log.User;
import com.example.weight_log.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // 全ユーザー取得
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // IDでユーザー取得
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ユーザー登録
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // 複数ユーザー登録
    @PostMapping("/bulk")
    public List<User> createUsers(@RequestBody List<User> users) {
    return userRepository.saveAll(users);
    }

    // ユーザー削除
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    // 複数ユーザー削除
    @DeleteMapping
    public void deleteUsers(@RequestBody List<Long> ids) {
        userRepository.deleteAllById(ids);
    }
    
    // ユーザー更新
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userRepository.findById(id)
            .map(user -> {
                user.setMyouji(updatedUser.getMyouji());
                user.setNamae(updatedUser.getNamae());
                user.setMyouji_kana(updatedUser.getMyouji_kana());
                user.setNamae_kana(updatedUser.getNamae_kana());
                user.setAge(updatedUser.getAge());
                user.setBirth_year(updatedUser.getBirth_year());
                user.setBirth_month(updatedUser.getBirth_month());
                user.setBirth_day(updatedUser.getBirth_day());
                return userRepository.save(user);
            })
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
