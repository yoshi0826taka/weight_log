package com.example.weight_log.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * ユーザー情報をクライアントに返却するための DTO。
 * パスワード等の機密情報は含めません。
 */
@Data
public class UserResponse {
    private Long id;
    private String myouji;
    private String namae;
    private String myouji_kana;
    private String namae_kana;
    private Integer age;
    private Integer birth_year;
    private Integer birth_month;
    private Integer birth_day;
    private String email;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
