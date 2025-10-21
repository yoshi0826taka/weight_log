package com.example.weight_log.controller;

import com.example.weight_log.model.User;
import com.example.weight_log.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkUserExists() {
        boolean exists = userService.existsAnyUser();
        return ResponseEntity.ok(exists);
    }

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.findById(id)
                .map(user -> {
                    user.setMyouji(updatedUser.getMyouji());
                    user.setNamae(updatedUser.getNamae());
                    user.setMyouji_kana(updatedUser.getMyouji_kana());
                    user.setNamae_kana(updatedUser.getNamae_kana());
                    user.setBirth_year(updatedUser.getBirth_year());
                    user.setBirth_month(updatedUser.getBirth_month());
                    user.setBirth_day(updatedUser.getBirth_day());
                    return ResponseEntity.ok(userService.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
