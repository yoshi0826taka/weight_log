package com.example.weight_log.service;

import com.example.weight_log.model.WeightRecord;
import com.example.weight_log.repository.WeightRecordRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
        return repository.save(record);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
