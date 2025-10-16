INSERT INTO users (
    myouji, 
    namae, 
    myouji_kana, 
    namae_kana, 
    birth_year, 
    birth_month, 
    birth_day, 
    created_at, 
    updated_at
)
VALUES (
    '山田', 
    '太郎', 
    'ヤマダ', 
    'タロウ', 
    1990, 
    1, 
    1, 
    CURRENT_TIMESTAMP, 
    CURRENT_TIMESTAMP
);
