package com.example.weight_log;

import java.sql.Timestamp;

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
    private Long id; // user_id

    private String myouji; // 姓
    private String namae; // 名
    private String myouji_kana; // セイ
    private String namae_kana; // メイ
    private Integer age; // 年齢
    private Integer birth_year; // 誕生年
    private Integer birth_month; // 誕生月
    private Integer birth_day; // 誕生日
    
    @Column(name = "created_at", updatable = false, insertable = false)
    private Timestamp created_at; // 作成日時
    
    @Column(name = "updated_at", insertable = false)
    private Timestamp updated_at; // 更新日時

    
}
