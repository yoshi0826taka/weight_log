package com.example.weight_log.service;

import com.example.weight_log.WeightRecord;
import com.example.weight_log.repository.WeightRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class WeightRecordService {
    
    private final WeightRecordRepository repository;

    // コンストラクタインジェクション
    public WeightRecordService(WeightRecordRepository repository) {
        this.repository = repository;
    }

    // ユーザーIDで体重記録を取得
    public List<WeightRecord> getRecordsByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    // 体重記録を追加
    public WeightRecord addRecord(Long userId, Double weight) {
        WeightRecord record = new WeightRecord(userId, weight, LocalDateTime.now());
        return repository.save(record);
    }

    // 体重記録を削除
    public void deleteRecord(Long id) {
        repository.deleteById(id);
    }

    // 体重記録を更新
    public WeightRecord updateRecord(Long id, Double newWeight) {
        WeightRecord record = repository.findById(id).orElseThrow();
        record.setWeight(newWeight);
        record.setRecordedAt(LocalDateTime.now());
        return repository.save(record);
    }

}
