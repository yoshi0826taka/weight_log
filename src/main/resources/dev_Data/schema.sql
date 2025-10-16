-- ユーザーテーブル
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    myouji VARCHAR(50) NOT NULL,       -- 苗字
    namae VARCHAR(50) NOT NULL,        -- 名前
    myouji_kana VARCHAR(50),           -- 苗字カナ
    namae_kana VARCHAR(50),            -- 名前カナ
    age	INT, 						   -- 年齢
    birth_year INT,                    -- 生年（西暦）
    birth_month INT,				   -- 生年(月)
    birth_day INT,					   -- 生年(日)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
