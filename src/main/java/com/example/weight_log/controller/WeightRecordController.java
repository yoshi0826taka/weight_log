package com.example.weight_log.controller;

import com.example.weight_log.WeightRecord;
import com.example.weight_log.service.WeightRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weight")
@CrossOrigin(origins = "*")
public class WeightRecordController {
    
    private final WeightRecordService service;

    public WeightRecordController(WeightRecordService service) {
        this.service = service;
    }

    // ユーザーIDで体重記録を取得
    @GetMapping("/{userId}")
    public List<WeightRecord> getRecords(@PathVariable Long userId) {
        return service.getRecordsByUser(userId);
    }

    // 体重記録を追加
    @PostMapping("/{userId}")
    public WeightRecord addRecord(@PathVariable Long userId, @RequestBody Double weight) {
        return service.addRecord(userId, weight);
    }

    // 体重記録を削除
    @DeleteMapping("/{recordId}")
    public void deleteRecord(@PathVariable Long recordId) {
        service.deleteRecord(recordId);
    }

    // 体重記録を更新
    @PutMapping("/{recordId}")
    public WeightRecord updateRecord(@PathVariable Long recordId, @RequestBody Double weight) {
        return service.updateRecord(recordId, weight);
    }

}
