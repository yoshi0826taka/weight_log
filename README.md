# weight_log プロジェクト

このリポジトリは体重記録アプリのバックエンド（Spring Boot）といくつかの静的フロントエンドを含みます。

概要
- 言語: Java 17
- フレームワーク: Spring Boot (starter web, data-jpa)
- DB: MySQL（開発設定は `src/main/resources/application.properties` を参照）
- フロント: 静的 HTML + TypeScript（`src/main/resources/static`）

主要ディレクトリ
- `src/main/java/com/example/weight_log/controller` — REST コントローラ
- `src/main/java/com/example/weight_log/service` — ビジネスロジック
- `src/main/java/com/example/weight_log/model` — JPA エンティティ
- `src/main/java/com/example/weight_log/repository` — Spring Data JPA リポジトリ
- `src/main/java/com/example/weight_log/dto` — 入出力用 DTO
- `src/main/resources` — application.properties, 静的リソース

セットアップ（ローカル）
1. MySQL を起動し、`application.properties` の接続情報を確認／変更する
2. （任意）`app.jwt.secret` を環境変数 `JWT_SECRET` へ移すことを推奨
3. ビルドと実行
```bash
./mvnw -DskipTests=true package
./mvnw spring-boot:run
```

テスト
- 現状、統合テストはローカルの MySQL に接続して DB を操作します。実行には DB が必要です。
- 早く回したい場合は `-DskipTests=true` を付けてビルドしてください。

重要な設計ポイント（開発者向けメモ）
- エンティティ(`User`, `WeightRecord`) と DTO(`UserRequest`, `UserResponse`) を分離しています。API は DTO を介してやり取りします。
- パスワードは `UserService.save` 内で BCrypt されます（既にハッシュ済みなら再ハッシュしないガードあり）。
- 認証は `AuthController` にてメール/パスワードで行い、発行された JWT をクライアントが `Authorization: Bearer <token>` として付与して利用する設計です（ただし JWT の検証フィルタは未実装のため保護は手動で行う必要があります）。
- OpenAPI（springdoc）依存は追加されています。`/swagger-ui/index.html` を確認してください（自動検出されます）。

開発のヒント
- 変更を加える場合はまず `./mvnw -DskipTests=true package` でコンパイルエラーを確認してください。
- テストを編集する際、バリデーション（`UserRequest` の注釈）に合わせてテスト用ペイロードを更新する必要があります。

連絡先 / 次の作業候補
- JWT の検証フィルタ実装（高優先）
- テストを H2 / Testcontainers に切り替えて CI を安定化（中）
- OpenAPI 注釈の強化と Swagger 説明の追記（低）

---
追加の詳細が必要なら知らせてください。README を拡張します。
