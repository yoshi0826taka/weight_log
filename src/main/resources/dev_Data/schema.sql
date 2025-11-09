-- ユーザーテーブル
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    myouji VARCHAR(50) NOT NULL,         -- 姓
    namae VARCHAR(50) NOT NULL,          -- 名
    myouji_kana VARCHAR(50),             -- 姓（カナ）
    namae_kana VARCHAR(50),              -- 名（カナ）
    email VARCHAR(255) UNIQUE,           -- メールアドレス（ログイン用）
    age INT,                             -- 年齢
    birth_year INT,                      -- 生年（西暦）
    birth_month INT,                     -- 誕生月
    birth_day INT,                       -- 誕生日
    password VARCHAR(255),               -- パスワード（ハッシュ化）
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE password_resets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    reset_token VARCHAR(255) NOT NULL,
    token_expiration TIMESTAMP NOT NULL,
    is_used BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_password_resets_user FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE weight_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,           -- id
    record_date DATE NOT NULL,         -- 記録日
    weight DECIMAL(5,2) NOT NULL,      -- 体重
    height DECIMAL(5,2),               -- 慎重
    bmi DECIMAL(4,2),                  -- BMI
    breakfast VARCHAR(255) NULL,       -- 朝食
    lunch VARCHAR(255) NULL,           -- 昼食
    dinner VARCHAR(255) NULL,          -- 夕食
    bust DECIMAL(5,2),                 -- バスト(cm)
    waist DECIMAL(5,2),                -- ウエスト(cm)
    hip DECIMAL(5,2),                  -- ヒップ(cm)
    arm DECIMAL(5,2),                  -- 二の腕(cm)
    thigh DECIMAL(5,2),                -- もも(cm)
    memo VARCHAR(255),                 -- 備考
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_weight_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
