package com.example.weight_log.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Data
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    // ユーザーid
    private Long id;

    // 姓
    private String myouji;

    // 名
    private String namae;

    // セイ
    private String myouji_kana;

    // メイ
    private String namae_kana;

    // 年齢
    private Integer age; 

    // 誕生年
    private Integer birth_year;

    // 誕生月
    private Integer birth_month;

    // 誕生日
    private Integer birth_day;
    
    // 作成日時
    private LocalDateTime created_at;

    // 更新日時
    private LocalDateTime updated_at;

    @PrePersist
    public void onCreate() {
        created_at = LocalDateTime.now(); 
        updated_at = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updated_at = LocalDateTime.now();
    }
    
}
