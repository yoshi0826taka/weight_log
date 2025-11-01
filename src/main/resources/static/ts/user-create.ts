document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("userForm") as HTMLFormElement | null;

  if (!form) {
    console.error("フォームが見つかりません。HTMLのidを確認してください。");
    return;
  }

  form.addEventListener("submit", async (event) => {
    event.preventDefault(); // ページリロード防止

    // --- 入力値の取得 ---
    const userData = {
      myouji: (document.getElementById("myouji") as HTMLInputElement)?.value.trim() || "",
      namae: (document.getElementById("namae") as HTMLInputElement)?.value.trim() || "",
      myouji_kana: (document.getElementById("myouji_kana") as HTMLInputElement)?.value.trim() || "",
      namae_kana: (document.getElementById("namae_kana") as HTMLInputElement)?.value.trim() || "",
      birth_year: Number((document.getElementById("birth_year") as HTMLInputElement)?.value),
      birth_month: Number((document.getElementById("birth_month") as HTMLInputElement)?.value),
      birth_day: Number((document.getElementById("birth_day") as HTMLInputElement)?.value),
    };

    // --- バリデーション ---
    const emptyField = Object.entries(userData).find(([key, value]) => {
      if (typeof value === "string") return value === "";
      if (typeof value === "number") return isNaN(value) || value <= 0;
      return false;
    });

    if (emptyField) {
      alert(`入力が不足しています: ${emptyField[0]}`);
      return;
    }

    console.log("送信データ:", userData);

    try {
      const response = await fetch("/api/users", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(userData),
      });

      if (!response.ok) {
        const errorText = await response.text();
        console.error("登録失敗:", errorText);

        if (response.status === 409) {
          alert("このユーザーは既に登録されています。");
        } else if (response.status >= 500) {
          alert("サーバーエラーが発生しました。管理者に連絡してください。");
        } else {
          alert("登録に失敗しました。入力内容を確認してください。");
        }
        return;
      }

      // --- 成功時処理 ---
      const savedUser = await response.json();
      console.log("登録成功:", savedUser);
      alert("ユーザー登録が完了しました！");
      window.location.href = "/userForm.html";

    } catch (error) {
      console.error("通信エラー:", error);
      alert("サーバーとの通信に失敗しました。ネットワークを確認してください。");
    }
  });
});
