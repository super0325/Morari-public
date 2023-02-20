fetch("/morari/guest/share/head.html")
  .then(response => response.text())
  .then(html => {
    // 將載入的 HTML 放入 .header 元素中
    document.querySelector("head").insertAdjacentHTML("beforeend", html);
  });