package com.example.weight_log.repository;

import com.example.weight_log.model.WeightRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * 体重記録（WeightRecord）用リポジトリ。
 * Spring Data JPA が基本的な CRUD 実装を提供します。
 */
public interface WeightRecordRepository extends JpaRepository<WeightRecord, Long> {
    /**
     * 指定ユーザーの記録を取得します。
     * @param userId ユーザーID
     * @return 指定ユーザーに紐づく記録一覧
     */
    List<WeightRecord> findByUserId(Long userId);
}
