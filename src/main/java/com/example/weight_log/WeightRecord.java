package com.example.weight_log;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wetight_records")
public class WeightRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Double weight;

    private LocalDateTime recordedAt;

    public WeightRecord() {}

    public WeightRecord(Long userId, Double weight, LocalDateTime recordedAt) {
        this.userId = userId;
        this.weight = weight;
        this.recordedAt = recordedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}
