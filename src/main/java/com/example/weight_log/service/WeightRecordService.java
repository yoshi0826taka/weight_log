package com.example.weight_log.service;

import com.example.weight_log.model.WeightRecord;
import com.example.weight_log.repository.WeightRecordRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * 体重記録（WeightRecord）に関する業務ロジックを扱うサービスクラス。
 * - 指定ユーザーの記録を取得、保存、削除する機能を提供します。
 */
@Service
public class WeightRecordService {

    private final WeightRecordRepository repository;

    public WeightRecordService(WeightRecordRepository repository) {
        this.repository = repository;
    }

    public List<WeightRecord> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    public Optional<WeightRecord> findById(Long id) {
        return repository.findById(id);
    }

    public WeightRecord save(WeightRecord record) {
        // 必要であればここで前処理（入力補正や検証）を行えます
        return repository.save(record);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
