package com.example.weight_log.dto;

import lombok.Data;

@Data
public class UserRequest {
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
    private String password;
}
