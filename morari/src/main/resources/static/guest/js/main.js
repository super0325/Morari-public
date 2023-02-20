(function ($) {
    "use strict";

    // Initiate the wowjs animation library
    new WOW().init();

    // Initiate menu
    $('#header').after('<div class="mobile-menu d-xl-none">');
    $('.top-menu').clone().appendTo('.mobile-menu');
    $('.mobile-menu-btn').click(function () {
        $('.mobile-menu').stop().slideToggle();
    });

    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 100) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({ scrollTop: 0 }, 1500, 'easeInOutExpo');
        return false;
    });

    //Portfolio modal slider
    $('.port-slider').delay(10000);
    $('.port-slider').slick({
        autoplay: true,
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: false,
        fade: true,
        asNavFor: '.port-slider-nav'
    });
    $('.port-slider-nav').slick({
        autoplay: true,
        slidesToShow: 5,
        slidesToScroll: 1,
        asNavFor: '.port-slider',
        arrows: false,
        dots: false,
        centerMode: true,
        focusOnSelect: true
    });

    $('#popover-content-download').hide();
    $("[data-toggle=popover]").each(function (e) {
        $(this).popover({
            html: true,
            content: function () {
                var id = $(this).attr('id')
                return $('#popover-content-' + id).html();
            }
        });

    });

    // Date and time picker
    $('#date-1, #date-2, #date-3, #date-4, #date-5, #date-6').datetimepicker({
        format: 'L'
    });
    $('#time-1, #time-2').datetimepicker({
        format: 'LT'
    });
})(jQuery);

window.onload = function () {
    document.body.style.display = 'block';
    // 登入狀態驗證
    fetch("/morari/api/auth/state", {
        method: "Get",
        // 發送請求時附帶Cookie
        credentials: "same-origin"
    })
        .then(response => response.json())
        .then(loginstate => {
            if (loginstate) {

                // 查看權限
                fetch('/morari/admin/camper/api/userroles')
                    .then(response => response.json())
                    .then(data => {
                        // 判斷是否有 SUPERADMIN 或 ADMIN 角色
                        if (data.some(role => role.authority === 'SUPERADMIN' || role.authority === 'ADMIN')) {
                            // 顯示編輯按鈕
                            document.getElementById('edit-button-header').style = '';
                            let mobile = document.querySelector('.mobile-menu')
                            mobile.querySelector('.setpage').style = "";
                            // document.getElementById('edit-button-fotter').style = '';
                        }
                    });

                // 登入
                fetch("/morari/guest/share/loginstate.html")
                    .then(response => response.text())
                    .then(html => {
                        // 將載入的 HTML 放入 .footer 元素中
                        document.getElementById("loginstate").innerHTML = html;
                        // 找用戶照片放入SRC
                        fetch("/morari/guest/camper/api/shot")
                            .then(response => response.text())
                            .then(shotUrl => {
                                document.querySelector('.userShot').src = shotUrl;
                            })
                        // 找UID放入href
                        fetch("/morari/utils/getuid")
                            .then(response => response.text())
                            .then(uid => {
                                document.querySelector('.camperpage a').href = "/morari/camper/" + uid;
                                let mobile = document.querySelector('.mobile-menu')
                                mobile.querySelector('.camperpage a').href = "/morari/camper/" + uid;
                            })

                    });
            } else {
                // 未登入
                document.getElementById("loginstate").innerHTML = '<a class="fa fa-user" href="/morari/login"> login</a>';
            }
        })
        .catch(error => {
            console.log('Error:', error);
        });


    // 監聽頁面上的點擊事件
    document.addEventListener("click", function (event) {
        let loginstate = document.getElementById("loginstate");
        let dropdownContent = document.getElementById("dropdown-content");
        if (event.target !== loginstate && event.target.parentNode !== loginstate &&
            event.target !== dropdownContent && event.target.parentNode !== dropdownContent) {
            dropdownContent.style.display = "none";
        }

    });


}
// 切換下拉選單顯示/隱藏
function toggleDropdown() {
    let dropdownContent = document.getElementById("dropdown-content");
    if (dropdownContent.style.display === "none") {
        dropdownContent.style.display = "block";
    } else {
        dropdownContent.style.display = "none";
    }
};
// SA快
function submitadmin() {
    const data = {
        email: "sa",
        password: "0000",
        rememberMe: true
    }
    sendlogin(data)
}

function sendlogin(data) {
    fetch("/morari/api/auth/authenticate", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
        .then(
            response => {

                if (response.status === 201) {
                    // success
                    window.location.href = '/morari/home';
                } else if (response.status === 401) {
                    document.getElementById("errormsg").innerHTML = "Invalid Email or Password";
                } else if (response.status === 403) {
                    document.getElementById("errormsg").innerHTML = "ser not authorized, please contact us.";
                } else {
                    document.getElementById("errormsg").innerHTML = "login failed";
                }
            })
        .catch(error => {
            console.error("Error:", error);
        });
}

function googlequick() {
    window.location.href = "/morari/login/oauth2/authorization/google"
}
