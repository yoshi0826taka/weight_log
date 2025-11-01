"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("userForm");
    if (!form) {
        console.error("フォームが見つかりません。HTMLのidを確認してください。");
        return;
    }
    form.addEventListener("submit", (event) => __awaiter(void 0, void 0, void 0, function* () {
        var _a, _b, _c, _d, _e, _f, _g;
        event.preventDefault();
        const userData = {
            myouji: ((_a = document.getElementById("myouji")) === null || _a === void 0 ? void 0 : _a.value.trim()) || "",
            namae: ((_b = document.getElementById("namae")) === null || _b === void 0 ? void 0 : _b.value.trim()) || "",
            myouji_kana: ((_c = document.getElementById("myouji_kana")) === null || _c === void 0 ? void 0 : _c.value.trim()) || "",
            namae_kana: ((_d = document.getElementById("namae_kana")) === null || _d === void 0 ? void 0 : _d.value.trim()) || "",
            birth_year: Number((_e = document.getElementById("birth_year")) === null || _e === void 0 ? void 0 : _e.value),
            birth_month: Number((_f = document.getElementById("birth_month")) === null || _f === void 0 ? void 0 : _f.value),
            birth_day: Number((_g = document.getElementById("birth_day")) === null || _g === void 0 ? void 0 : _g.value),
        };
        const emptyField = Object.entries(userData).find(([key, value]) => {
            if (typeof value === "string")
                return value === "";
            if (typeof value === "number")
                return isNaN(value) || value <= 0;
            return false;
        });
        if (emptyField) {
            alert(`入力が不足しています: ${emptyField[0]}`);
            return;
        }
        console.log("送信データ:", userData);
        try {
            const response = yield fetch("/api/users", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(userData),
            });
            if (!response.ok) {
                const errorText = yield response.text();
                console.error("登録失敗:", errorText);
                if (response.status === 409) {
                    alert("このユーザーは既に登録されています。");
                }
                else if (response.status >= 500) {
                    alert("サーバーエラーが発生しました。管理者に連絡してください。");
                }
                else {
                    alert("登録に失敗しました。入力内容を確認してください。");
                }
                return;
            }
            const savedUser = yield response.json();
            console.log("登録成功:", savedUser);
            alert("ユーザー登録が完了しました！");
            window.location.href = "/userForm.html";
        }
        catch (error) {
            console.error("通信エラー:", error);
            alert("サーバーとの通信に失敗しました。ネットワークを確認してください。");
        }
    }));
});
//# sourceMappingURL=user-create.js.map