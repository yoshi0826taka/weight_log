package com.example.weight_log.controller;

import com.example.weight_log.dto.LoginRequest;
import com.example.weight_log.dto.LoginResponse;
import com.example.weight_log.model.User;
import com.example.weight_log.security.JwtUtil;
import com.example.weight_log.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * 認証用コントローラ。
 * - /api/auth/login エンドポイントを提供し、メールアドレスとパスワードで認証を行う。
 * - 認証に成功した場合は JWT トークンを返却する。
 *
 * クライアント: メールアドレス / パスワードを送信し、受け取った token を Authorization ヘッダに
 * "Bearer <token>" として付与して保護された API にアクセスします。
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * ログイン処理。成功すると JWT を返す。
     *
     * @param req リクエスト（email, password）
     * @return 成功: 200 + { token: "..." } / 失敗: 401
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        Optional<User> opt = userService.findByEmail(req.getEmail());
        if (opt.isEmpty()) {
            return ResponseEntity.status(401).body("invalid credentials");
        }
        User u = opt.get();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(req.getPassword(), u.getPassword())) {
            return ResponseEntity.status(401).body("invalid credentials");
        }
        String token = jwtUtil.generateToken(String.valueOf(u.getId()));
        LoginResponse res = new LoginResponse();
        res.setToken(token);
        return ResponseEntity.ok(res);
    }
}
