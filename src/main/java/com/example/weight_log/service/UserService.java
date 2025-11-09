package com.example.weight_log.service;

import com.example.weight_log.model.User;
import com.example.weight_log.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2[aby]\\$.{56}\\z");

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        // Hash password if provided and not already bcrypt
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            String pw = user.getPassword();
            if (!BCRYPT_PATTERN.matcher(pw).matches()) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                user.setPassword(encoder.encode(pw));
            }
        }
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsAnyUser() {
        return userRepository.count() > 0;
    }

}
