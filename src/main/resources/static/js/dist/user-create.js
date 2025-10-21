var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
const form = document.getElementById("userForm");
const apiBase = "http://localhost:8080/api/users";
// ✅ 起動時にユーザーが存在するかチェック
window.addEventListener("DOMContentLoaded", () => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const res = yield fetch(`${apiBase}/exists`);
        const exists = yield res.json();
        if (exists) {
            // 既にユーザーが登録済み → ユーザー選択画面へ
            window.location.href = "user-select.html";
        }
    }
    catch (error) {
        console.error("ユーザー存在確認に失敗しました:", error);
    }
}));
// ✅ 登録処理
form.addEventListener("submit", (event) => __awaiter(void 0, void 0, void 0, function* () {
    event.preventDefault();
    const user = {
        myouji: document.getElementById("myouji").value,
        namae: document.getElementById("namae").value,
        myouji_kana: document.getElementById("myouji_kana").value,
        namae_kana: document.getElementById("namae_kana").value,
        birth_year: Number(document.getElementById("birth_year").value),
        birth_month: Number(document.getElementById("birth_month").value),
        birth_day: Number(document.getElementById("birth_day").value),
    };
    try {
        const res = yield fetch(apiBase, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(user),
        });
        if (!res.ok)
            throw new Error("ユーザー登録に失敗しました");
        alert("登録が完了しました！");
        window.location.href = "user-select.html";
    }
    catch (error) {
        console.error(error);
        alert("登録時にエラーが発生しました");
    }
}));
export {};
