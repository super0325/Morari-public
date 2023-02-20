var file;
// 載入 你的.html
fetch("/morari/camper/html/guestedit.html")
    .then(response => response.text())
    .then(html => {
        // 將載入的 HTML 放入 .guestedit 元素中
        document.querySelector(".guestedit").innerHTML = html;
        fetch("/morari/admin/camper/api/guestdetail")
            .then(response => response.json())
            .then(data => {
                // document.getElementById("uid").textContent = data.uid;
                document.getElementById("titleuid").innerText = data.uid;
                document.getElementById("uid").value = data.uid;
                document.getElementById("nickname").value = data.nickname;
                document.getElementById("firstname").value = data.firstname;
                document.getElementById("lastname").value = data.lastname;
                document.getElementById("email").value = data.email;
                document.getElementById("phone").value = data.phone;
                if (data.birthday) {
                    let birthday = moment(Date.parse(data.birthday)).format('YYYY-MM-DD');
                    document.getElementById("birthday").value = birthday;
                }
                document.getElementById("address").value = data.address;
                document.getElementById("gender").value = data.gender;
                document.getElementById("exp").value = data.exp;
                document.getElementById("level").value = data.level;
                document.getElementById("point").value = data.point;
                document.getElementById("subscribed").src = data.subscribed;
                document.getElementById("shotimg").src = data.shot;
                document.getElementById("shot").value = data.shot;
                document.getElementById("about").value = data.about;
            })
        document.getElementById("shotimg").addEventListener("click", function () {
            document.getElementById("shotInput").click();
        });
        document.getElementById("shotInput").addEventListener("change", function () {
            file = this.files[0];
            let reader = new FileReader();
            reader.onload = function (e) {
                document.getElementById("shotimg").src = e.target.result;
            };
            reader.readAsDataURL(file);
        });
    });
// 儲存用戶資訊
function saveedit() {
    let formData = new FormData();
    formData.append("file", file);
    formData.append("uid", document.getElementById("uid").value);
    if (file) {
        fetch("/morari/guest/camper/api/shot", {
            method: "PUT",
            body: formData
        }).then(response => response.text())
            .then(a => {
                document.getElementById("shot").value = a;
                getuservalue();
            })
    } else {
        getuservalue();
    }
}
// 封裝用戶資訊並送出
function getuservalue() {
    let uid = document.getElementById("uid").value
    let data = {
        uid: uid,
        nickname: document.getElementById("nickname").value,
        firstname: document.getElementById("firstname").value,
        lastname: document.getElementById("lastname").value,
        email: document.getElementById("email").value,
        phone: document.getElementById("phone").value,
        birthday: document.getElementById("birthday").value,
        address: document.getElementById("address").value,
        gender: document.getElementById("gender").value,
        exp: document.getElementById("exp").value,
        level: document.getElementById("level").value,
        point: document.getElementById("point").value,
        subscribed: document.getElementById("subscribed").value,
        shot: document.getElementById("shot").value,
        about: document.getElementById("about").value,
    };
    fetch("/morari/guest/camper/api/userdetail/" + uid, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.status == 200) {
                alert('修改成功');
                window.location.reload();
            } else {
                alert('修改失敗');
                window.location.reload();
            }
        })
}