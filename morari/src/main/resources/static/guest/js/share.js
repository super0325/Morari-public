fetch("/morari/guest/share/header.html")
  .then(response => response.text())
  .then(html => {
    // 將載入的 HTML 放入 .header 元素中
    document.querySelector(".header").innerHTML = html;
    let currentURL = window.location.href;

    // 判斷當前網頁的位置
    // if (currentURL.indexOf("home") !== -1) {
    //   // 如果當前網頁是首頁，則將 "home" 選項標記為 "active"
    //   document.querySelector(".homepage").classList.add("active");
    // }
    if (currentURL.indexOf("about") !== -1) {
      // 如果當前網頁是 "About" 頁面，則將 "about" 選項標記為 "active"
      document.querySelector(".aboutpage").classList.add("active");
    }
    else if (currentURL.indexOf("team") !== -1) {
      // 如果當前網頁是 "Team" 頁面，則將 "team" 選項標記為 "active"
      document.querySelector(".teampage").classList.add("active");
    }
    else if (currentURL.indexOf("camper") !== -1) {
      // 如果當前網頁是 "Team" 頁面，則將 "camper" 選項標記為 "active"
      document.querySelector(".camperpage").classList.add("active");
    }
    else if (currentURL.indexOf("camp") !== -1) {
      // 如果當前網頁是 "Team" 頁面，則將 "camp" 選項標記為 "active"
      document.querySelector(".camppage").classList.add("active");
    }
    else if (currentURL.indexOf("forum") !== -1) {
      // 如果當前網頁是 "Team" 頁面，則將 "forum" 選項標記為 "active"
      document.querySelector(".forumpage").classList.add("active");
    }
    else if (currentURL.indexOf("mall") !== -1) {
      // 如果當前網頁是 "Team" 頁面，則將 "mall" 選項標記為 "active"
      document.querySelector(".mallpage").classList.add("active");
    }
    else if (currentURL.indexOf("work") !== -1) {
      // 如果當前網頁是 "Team" 頁面，則將 "work" 選項標記為 "active"
      document.querySelector(".workpage").classList.add("active");
    }
    else if (currentURL.indexOf("login") !== -1) {
      // 如果當前網頁是 "Team" 頁面，則將 "login" 選項標記為 "active"
      document.querySelector(".loginpage").classList.add("active");
    }
  });

// 載入 footer.html
fetch("/morari/guest/share/footer.html")
  .then(response => response.text())
  .then(html => {
    // 將載入的 HTML 放入 .footer 元素中
    document.querySelector(".footer").innerHTML = html;
  });
  

  
  