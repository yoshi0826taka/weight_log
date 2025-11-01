document.addEventListener("DOMContentLoaded", async () => {
  const userListDiv = document.getElementById("userList")!;
  const addUserBtn = document.getElementById("addUserBtn")!;

  try {
    const response = await fetch("/api/users");
    const users = await response.json();

    if (users.length === 0) {
      userListDiv.innerHTML = "<p>登録されているユーザーがいません。</p>";
      return;
    }

    const ul = document.createElement("ul");
    users.forEach((user: any) => {
      const li = document.createElement("li");
      li.textContent = `${user.myouji} ${user.namae}`;
      li.classList.add("user-item");

      li.addEventListener("click", () => {
        // ✅ 選択したユーザーIDをURLパラメータに付けて遷移
        window.location.href = `/weightRecord.html?userId=${user.id}`;
      });

      ul.appendChild(li);
    });

    userListDiv.innerHTML = ""; // 「読み込み中...」を消去
    userListDiv.appendChild(ul);

  } catch (error) {
    console.error("ユーザー一覧取得エラー:", error);
    userListDiv.innerHTML = "<p>ユーザー一覧の取得に失敗しました。</p>";
  }

  addUserBtn.addEventListener("click", () => {
    window.location.href = "/newUserCreate.html";
  });
});
