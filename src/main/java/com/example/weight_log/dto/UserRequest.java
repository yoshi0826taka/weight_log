
package com.example.weight_log.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * ユーザー作成・更新時にクライアントから受け取るリクエスト用 DTO。
 * バリデーションアノテーションを付与しており、Controller で `@Valid` による検証を行います。
 */
@Data
public class UserRequest {
    private Long id;

    @NotBlank(message = "姓(myōji)は必須です")
    @Size(max = 50)
    private String myouji;

    @NotBlank(message = "名(namae)は必須です")
    @Size(max = 50)
    private String namae;

    @Size(max = 50)
    private String myouji_kana;

    @Size(max = 50)
    private String namae_kana;

    private Integer age;

    @NotNull(message = "生年(birth_year)は必須です")
    @Min(1900)
    @Max(2100)
    private Integer birth_year;

    @NotNull(message = "生月(birth_month)は必須です")
    @Min(1)
    @Max(12)
    private Integer birth_month;

    @NotNull(message = "生日(birth_day)は必須です")
    @Min(1)
    @Max(31)
    private Integer birth_day;

    @Email(message = "有効なメールアドレスを入力してください")
    @NotBlank(message = "emailは必須です")
    @Size(max = 255)
    private String email;

    @NotBlank(message = "passwordは必須です")
    @Size(min = 6, max = 255, message = "passwordは6文字以上で入力してください")
    private String password;
}
