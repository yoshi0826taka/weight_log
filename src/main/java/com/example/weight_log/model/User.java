package com.example.weight_log.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

    // メールアドレス（ログイン用）
    @Column(unique = true)
    private String email;

    // パスワード（ハッシュ化）
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

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
