// 載入 你的.html
fetch("/morari/camper/html/notlogin.html")
    .then(response => response.text())
    .then(html => {
        document.querySelector(".guestpage").innerHTML = html;
    });