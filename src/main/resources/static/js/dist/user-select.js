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
document.addEventListener("DOMContentLoaded", () => __awaiter(void 0, void 0, void 0, function* () {
    const userListDiv = document.getElementById("userList");
    const addUserBtn = document.getElementById("addUserBtn");
    try {
        const response = yield fetch("/api/users");
        const users = yield response.json();
        if (users.length === 0) {
            userListDiv.innerHTML = "<p>登録されているユーザーがいません。</p>";
            return;
        }
        const ul = document.createElement("ul");
        users.forEach((user) => {
            const li = document.createElement("li");
            li.textContent = `${user.myouji} ${user.namae}`;
            li.classList.add("user-item");
            li.addEventListener("click", () => {
                window.location.href = `/weightRecord.html?userId=${user.id}`;
            });
            ul.appendChild(li);
        });
        userListDiv.innerHTML = "";
        userListDiv.appendChild(ul);
    }
    catch (error) {
        console.error("ユーザー一覧取得エラー:", error);
        userListDiv.innerHTML = "<p>ユーザー一覧の取得に失敗しました。</p>";
    }
    addUserBtn.addEventListener("click", () => {
        window.location.href = "/newUserCreate.html";
    });
}));
//# sourceMappingURL=user-select.js.map