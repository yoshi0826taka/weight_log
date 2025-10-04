package com.example.weight_log;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp; 

@Entity
@Table(name = "weight_records")
@Data
public class WeightRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // user_id 

    @ManyToOne(fetch = FetchType.LAZY)                  // usersテーブルと関連付け
    @JoinColumn(name = "user_id", nullable = false)     // usersテーブルのuser_idカラムと関連付け
    private User user; // ユーザークラス

    private Date recordDate; // 記録日
    private Double weight;  // 体重
    private Double height; // 身長
    private Double bmi; // BMI

    private String breakfast; // 朝食
    private String lunch; // 昼食
    private String dinner;  // 夕食

    private Double bust; // バスト
    private Double waist; // ウエスト
    private Double hip; // ヒップ
    private Double arm; // 腕周り
    private Double thigh; // 太もも

    private String memo; // 備考

    private Timestamp createdAt; // 作成日時
    private Timestamp updatedAt; // 更新日時

}
