package com.example.weight_log.dto;

import lombok.Data;

/**
 * 認証成功時に返却するレスポンス DTO。JWT トークンを含みます。
 */
@Data
public class LoginResponse {
    private String token;
}
