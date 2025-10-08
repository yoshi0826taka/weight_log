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
    
    // 性別
    @Column(name = "created_at", updatable = false, insertable = false)
    private Timestamp created_at;

    // 登録日時
    @Column(name = "updated_at", insertable = false)
    private Timestamp updated_at;
    
}
