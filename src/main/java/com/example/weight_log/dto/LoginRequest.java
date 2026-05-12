package com.example.weight_log.dto;

import lombok.Data;

/**
 * 認証用ログインリクエスト DTO。
 * クライアントは email と password を送信します。
 */
@Data
public class LoginRequest {
    private String email;
    private String password;
}
