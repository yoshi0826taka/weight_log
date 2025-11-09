package com.example.weight_log.service;

import com.example.weight_log.model.User;
import com.example.weight_log.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * ユーザー関連の業務ロジックを提供するサービスクラス。
 * 主にリポジトリ経由でユーザーの検索・作成・更新・削除を行い、
 * パスワードのハッシュ化（BCrypt）などの横断的な処理を担います。
 *
 * 想定される利用:
 * - コントローラからの入力を受けて永続化を行う
 * - パスワードを安全に保存するためにハッシュ化を行う
 */
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

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * ユーザーを保存します。パスワードがプレーンテキストで渡された場合は
     * BCrypt でハッシュ化して保存します。既にハッシュ済みの文字列は再ハッシュしません。
     *
     * @param user 保存対象のユーザーエンティティ
     * @return 保存されたユーザーエンティティ
     */
    public User save(User user) {
        // パスワードが渡された場合は BCrypt ハッシュ化する（既にハッシュ済みなら再ハッシュしない）
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
