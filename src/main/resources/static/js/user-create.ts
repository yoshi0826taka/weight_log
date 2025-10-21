export {};
const form = document.getElementById("userForm") as HTMLFormElement;
const apiBase = "http://localhost:8080/api/users";

// ✅ 起動時にユーザーが存在するかチェック
window.addEventListener("DOMContentLoaded", async () => {
  try {
    const res = await fetch(`${apiBase}/exists`);
    const exists = await res.json();

    if (exists) {
      // 既にユーザーが登録済み → ユーザー選択画面へ
      window.location.href = "user-select.html";
    }
  } catch (error) {
    console.error("ユーザー存在確認に失敗しました:", error);
  }
});

// ✅ 登録処理
form.addEventListener("submit", async (event) => {
  event.preventDefault();

  const user = {
    myouji: (document.getElementById("myouji") as HTMLInputElement).value,
    namae: (document.getElementById("namae") as HTMLInputElement).value,
    myouji_kana: (document.getElementById("myouji_kana") as HTMLInputElement).value,
    namae_kana: (document.getElementById("namae_kana") as HTMLInputElement).value,
    birth_year: Number((document.getElementById("birth_year") as HTMLInputElement).value),
    birth_month: Number((document.getElementById("birth_month") as HTMLInputElement).value),
    birth_day: Number((document.getElementById("birth_day") as HTMLInputElement).value),
  };

  try {
    const res = await fetch(apiBase, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(user),
    });

    if (!res.ok) throw new Error("ユーザー登録に失敗しました");

    alert("登録が完了しました！");
    window.location.href = "user-select.html";
  } catch (error) {
    console.error(error);
    alert("登録時にエラーが発生しました");
  }
});
