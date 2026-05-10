package com.example.weight_log.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * Spring Security の設定クラス。
 *
 * 機能:
 * - JWT ベースのステートレス認証
 * - CORS 設定の一元管理
 * - エンドポイント別の認可ルール定義（@PreAuthorize を使用）
 * - JWT フィルタの設定
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * HTTP セキュリティ設定。
     * - JWT フィルタを追加
     * - CORS を有効化
     * - ステートレス（セッションなし）に設定
     * - 認可ルールを定義
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF 無効化（JWT ベースの API では不要）
            .csrf(csrf -> csrf.disable())

            // CORS 設定を適用
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // ステートレス: セッションを使用しない
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 認可ルール
            .authorizeHttpRequests(authz -> authz
                    // 認証不要なエンドポイント
                    .requestMatchers("/api/auth/login").permitAll()
                    .requestMatchers("/api/users/exists").permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                    .requestMatchers("/", "/index.html", "/css/**", "/js/**", "/ts/**").permitAll()

                    // その他すべては認証が必要（@PreAuthorize でさらに細かく制御）
                    .anyRequest().authenticated()
            )

            // JWT フィルタを UsernamePasswordAuthenticationFilter の前に追加
            .addFilterBefore(
                    new JwtAuthenticationFilter(jwtUtil),
                    UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    /**
     * CORS 設定。
     * - 本番環境では許可オリジンを限定してください
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 開発環境: ローカルホスト許可
        // 本番環境: 環境変数で制御することを推奨
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * パスワードエンコーダ（BCrypt）。
     * UserService と AuthController で利用します。
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
