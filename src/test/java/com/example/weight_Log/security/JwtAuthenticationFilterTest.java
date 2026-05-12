package com.example.weight_log.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import com.example.weight_log.model.User;
import com.example.weight_log.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * JWT 認証フィルタの統合テスト。
 * - トークン発行（ログイン）
 * - トークンを使用した保護エンドポイントへのアクセス
 * - 無効なトークンでのアクセス拒否
 * - トークン없이のアクセス拒否
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("JWT Authentication Filter Tests")
public class JwtAuthenticationFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String validToken;
    private Long testUserId;
    private final String TEST_EMAIL = "jwt-test@example.com";
    private final String TEST_PASSWORD = "password123";
    
    /**
     * テスト前のセットアップ:
     * - ユーザーデータベースをクリーンアップ
     * - テスト用ユーザーを作成
     * - 有効な JWT トークンを生成
     */
    @BeforeEach
    void setup() {
        userRepository.deleteAll();

        // テスト用ユーザーを作成（パスワードはハッシュ化して保存）
        User user = new User();
        user.setMyouji("TestMyouji");
        user.setNamae("TestNamae");
        user.setBirth_year(1990);
        user.setBirth_month(1);
        user.setBirth_day(1);
        user.setEmail(TEST_EMAIL);
        user.setPassword(passwordEncoder.encode(TEST_PASSWORD));
        User saved = userRepository.save(user);
        testUserId = saved.getId();

        // 有効なトークンを生成
        validToken = jwtUtil.generateToken(String.valueOf(testUserId));
    }

    /**
     * ログイン成功で JWT トークンが発行されることを確認するテスト。
     * @throws Exception
     */
    @Test
    @DisplayName("ログイン成功でJWTトークンが発行される")
    void testLoginSuccess() throws Exception {
        String loginJson = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\"}",
                TEST_EMAIL, TEST_PASSWORD
        );

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    /**
     * 無効なメールアドレスでのログイン失敗を確認するテスト。
     * @throws Exception
     */
    @Test
    @DisplayName("無効なメールアドレスでのログイン失敗")
    void testLoginFailureWithInvalidEmail() throws Exception {
        String loginJson = "{\"email\":\"invalid@example.com\",\"password\":\"password123\"}";

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isUnauthorized());
    }

    /**
     * 無効なパスワードでのログイン失敗を確認するテスト。
     * @throws Exception
     */
    @Test
    @DisplayName("無効なパスワードでのログイン失敗")
    void testLoginFailureWithInvalidPassword() throws Exception {
        String loginJson = String.format(
                "{\"email\":\"%s\",\"password\":\"wrongpassword\"}",
                TEST_EMAIL
        );

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isUnauthorized());
    }

    /**
     * 有効なトークンで保護されたエンドポイントにアクセスできることを確認するテスト。
     * @throws Exception
     */
    @Test
    @DisplayName("有効なトークンで保護エンドポイントにアクセス可能")
    void testAccessProtectedEndpointWithValidToken() throws Exception {
        mockMvc.perform(get("/api/users")
                .header("Authorization", "Bearer " + validToken))
                .andExpect(status().isOk());
    }

    /**
     * 無効なトークンで保護エンドポイントにアクセスできないことを確認するテスト。
     * @throws Exception
     */
    @Test
    @DisplayName("無効なトークンで保護エンドポイントアクセス拒否")
    void testAccessProtectedEndpointWithInvalidToken() throws Exception {
        mockMvc.perform(get("/api/users")
                .header("Authorization", "Bearer invalid.token.here"))
                .andExpect(status().isForbidden());
    }

    /**
     * トークンなしで保護されたエンドポイントにアクセスできないことを確認するテスト。
     * @throws Exception
     */
    @Test
    @DisplayName("トークンなしで保護エンドポイントアクセス拒否")
    void testAccessProtectedEndpointWithoutToken() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isForbidden());
    }

    /**
     * Bearer 形式でない Authorization ヘッダーが無視されることを確認するテスト。
     * @throws Exception
     */
    @Test
    @DisplayName("Bearer形式でないヘッダーは無視される")
    void testInvalidAuthorizationHeaderFormat() throws Exception {
        mockMvc.perform(get("/api/users")
                .header("Authorization", "Basic " + validToken))
                .andExpect(status().isForbidden());
    }

    /**
     * 存在確認エンドポイントはトークン不要であることを確認するテスト。
     * @throws Exception
     */
    @Test
    @DisplayName("存在確認エンドポイントはトークン不要")
    void testUserExistsEndpointDoesNotRequireToken() throws Exception {
        mockMvc.perform(get("/api/users/exists"))
                .andExpect(status().isOk());
    }

    /**
     * 体重記録取得エンドポイントは認証が必要であることを確認するテスト。
     * @throws Exception
     */
    @Test
    @DisplayName("体重記録取得は認証が必要")
    void testWeightRecordEndpointRequiresAuthentication() throws Exception {
        // トークンなしで拒否
        mockMvc.perform(get("/api/records/user/" + testUserId))
                .andExpect(status().isForbidden());

        // 有効なトークンで成功
        mockMvc.perform(get("/api/records/user/" + testUserId)
                .header("Authorization", "Bearer " + validToken))
                .andExpect(status().isOk());
    }
}
