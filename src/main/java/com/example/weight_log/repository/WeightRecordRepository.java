package com.example.weight_log.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.weight_log.WeightRecord;

import java.util.List;

public interface WeightRecordRepository extends JpaRepository<WeightRecord, Long> {
    List<WeightRecord> findByUserId(Long userId);
}
