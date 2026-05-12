package com.example.weight_log.controller;

import com.example.weight_log.model.WeightRecord;
import com.example.weight_log.service.WeightRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体重記録の REST API を提供するコントローラ。
 * 主な機能:
 * - ユーザーごとの記録一覧取得
 * - 記録の作成 / 更新 / 削除
 */
@RestController
@RequestMapping("/api/records")
@CrossOrigin(origins = "*")
public class WeightRecordController {

    private final WeightRecordService recordService;

    public WeightRecordController(WeightRecordService recordService) {
        this.recordService = recordService;
    }

    /**
     * 指定ユーザーの体重記録一覧を返す。
     * @param userId ユーザーID
     */
    @GetMapping("/user/{userId}")
    public List<WeightRecord> getUserRecords(@PathVariable Long userId) {
        return recordService.findByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<WeightRecord> addRecord(@RequestBody WeightRecord record) {
        return ResponseEntity.ok(recordService.save(record));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeightRecord> updateRecord(@PathVariable Long id, @RequestBody WeightRecord updatedRecord) {
        return recordService.findById(id)
                .map(record -> {
                    record.setWeight(updatedRecord.getWeight());
                    record.setRecord_date(updatedRecord.getRecord_date());
                    record.setCondition(updatedRecord.getCondition());
                    return ResponseEntity.ok(recordService.save(record));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        recordService.delete(id);
        return ResponseEntity.ok().build();
    }
}
