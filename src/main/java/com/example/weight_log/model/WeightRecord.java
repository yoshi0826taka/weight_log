package com.example.weight_log.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 体重記録を表すエンティティ。
 * テーブル: weight_records
 * フィールド:
 * - user: 記録の所有者（User エンティティへの多対一）
 * - record_date: 記録日
 * - weight: 体重（kg）
 * - condition: 健康状態やメモ
 */
@Entity
@Table(name = "weight_records")
@Data
public class WeightRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDate record_date;
    private Double weight;

    @Column(name = "health_condition")
    private String condition;

    private LocalDateTime created_at;
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
