package com.example.weight_log.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JWT（JSON Web Token）を生成するユーティリティクラス。
 * - アプリ内で発行するアクセストークンの作成を行う
 * - 簡易的に subject（ここではユーザーID）を設定してトークンを返す
 *
 * 注意: 本番ではシークレットの管理を環境変数やシークレットストアで行ってください。
 */
@Component
public class JwtUtil {

    @Value("${app.jwt.secret:changeit}")
    private String secret;

    @Value("${app.jwt.expiration-ms:3600000}")
    private long expirationMs;

    private Key getSigningKey() {
        // Use the provided secret (keep it long enough in production)
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 指定した subject（例: ユーザーID）から署名済みの JWT を生成します。
     *
     * @param subject トークンに埋め込むサブジェクト（文字列）
     * @return 生成された JWT 文字列
     */
    public String generateToken(String subject) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
