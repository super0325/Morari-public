// 載入 你的.html
fetch("camp/html/test.html")
    .then(response => response.text())
    .then(html => {
        // 將載入的 HTML 放入 .footer 元素中
        document.querySelector(".test").innerHTML = html;
    });