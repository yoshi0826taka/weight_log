package com.example.weight_log.repository;

import com.example.weight_log.model.WeightRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WeightRecordRepository extends JpaRepository<WeightRecord, Long> {
    List<WeightRecord> findByUserId(Long userId);
}
