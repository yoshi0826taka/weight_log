package com.example.weight_log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.weight_log.model.User;

/**
 * User エンティティの永続化操作を定義するリポジトリインタフェース。
 * Spring Data JPA が実装を自動生成します。
 */
public interface UserRepository extends JpaRepository<User, Long> {
	/**
	 * メールアドレスでユーザーを検索します（ログイン処理で使用）。
	 * @param email メールアドレス
	 * @return 見つかったユーザー（存在しない場合は Optional.empty）
	 */
	java.util.Optional<User> findByEmail(String email);

}
