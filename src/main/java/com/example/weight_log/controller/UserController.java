package com.example.weight_log.controller;

import com.example.weight_log.dto.UserRequest;
import com.example.weight_log.dto.UserResponse;
import com.example.weight_log.model.User;
import com.example.weight_log.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ユーザー管理用の REST コントローラ。
 * 提供する主な機能:
 * - ユーザー一覧の取得
 * - 単一ユーザーの取得/作成/更新/削除
 *
 * 入力は `UserRequest`、出力は `UserResponse` を使用してエンティティの内部情報（password など）を露出しない設計にしています。
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * 初期ユーザーの存在確認用エンドポイント。
     * フロントエンドの初期化処理などで使用します。
     */
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkUserExists() {
        boolean exists = userService.existsAnyUser();
        return ResponseEntity.ok(exists);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * ユーザー作成。
     * バリデーションは `UserRequest` に定義されています。
     */
    @PostMapping
    public ResponseEntity<UserResponse> addUser(@jakarta.validation.Valid @RequestBody UserRequest req) {
        User user = toEntity(req);
        User saved = userService.save(user);
        return ResponseEntity.ok(toDto(saved));
    }

    /**
     * ユーザー更新（部分更新を許容）。
     * クライアントは更新したいフィールドのみ送信できます。
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @jakarta.validation.Valid @RequestBody UserRequest updatedUser) {
        return userService.findById(id)
                .map(user -> {
                    // copy updatable fields
                    if (updatedUser.getMyouji() != null) user.setMyouji(updatedUser.getMyouji());
                    if (updatedUser.getNamae() != null) user.setNamae(updatedUser.getNamae());
                    if (updatedUser.getMyouji_kana() != null) user.setMyouji_kana(updatedUser.getMyouji_kana());
                    if (updatedUser.getNamae_kana() != null) user.setNamae_kana(updatedUser.getNamae_kana());
                    if (updatedUser.getBirth_year() != null) user.setBirth_year(updatedUser.getBirth_year());
                    if (updatedUser.getBirth_month() != null) user.setBirth_month(updatedUser.getBirth_month());
                    if (updatedUser.getBirth_day() != null) user.setBirth_day(updatedUser.getBirth_day());
                    if (updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());
                    if (updatedUser.getPassword() != null) user.setPassword(updatedUser.getPassword());
                    return ResponseEntity.ok(toDto(userService.save(user)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    private UserResponse toDto(User u) {
        // エンティティから外部公開用 DTO に変換するヘルパー
        // password や内部の管理情報は DTO に含めないこと
        UserResponse r = new UserResponse();
        r.setId(u.getId());
        r.setMyouji(u.getMyouji());
        r.setNamae(u.getNamae());
        r.setMyouji_kana(u.getMyouji_kana());
        r.setNamae_kana(u.getNamae_kana());
        r.setAge(u.getAge());
        r.setBirth_year(u.getBirth_year());
        r.setBirth_month(u.getBirth_month());
        r.setBirth_day(u.getBirth_day());
        r.setEmail(u.getEmail());
        r.setCreated_at(u.getCreated_at());
        r.setUpdated_at(u.getUpdated_at());
        return r;
    }

    private User toEntity(UserRequest req) {
        // リクエスト DTO から新規 User エンティティを作る
        // 注意: パスワードはプレーンで入るため、保存時にサービス層でハッシュ化されます
        User u = new User();
        u.setMyouji(req.getMyouji());
        u.setNamae(req.getNamae());
        u.setMyouji_kana(req.getMyouji_kana());
        u.setNamae_kana(req.getNamae_kana());
        u.setAge(req.getAge());
        u.setBirth_year(req.getBirth_year());
        u.setBirth_month(req.getBirth_month());
        u.setBirth_day(req.getBirth_day());
        u.setEmail(req.getEmail());
        u.setPassword(req.getPassword());
        return u;
    }
}
