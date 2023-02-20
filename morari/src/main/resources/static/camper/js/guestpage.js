// 載入 你的.html
let currentURL = window.location.href;
var uid = currentURL.substring(currentURL.lastIndexOf("/") + 1);
fetch("/morari/camper/html/guestpage.html")
    .then(response => response.text())
    .then(html => {
        document.querySelector(".guestpage").innerHTML = html;
        if (uid!=="notlogin") {
            fetch("/morari/guest/camper/api/userdetail/"+uid)
            .then(response => response.json())
            .then(data=>{
                // document.getElementById("uid").textContent = data.uid;
                document.getElementById("nickname").textContent = data.nickname;
                document.getElementById("exp").textContent = "Exp : "+data.exp;
                document.getElementById("level").textContent = "Level : "+data.level;
                document.getElementById("point").textContent = "Point : "+data.point;
                document.getElementById("campershot").src = data.shot;
                document.getElementById("about").textContent = data.about;
                
                $.ajax({
					url: "/morari/showpostnonhidebyuserid.controller/" + uid,
					dataType: "JSON",
					contentType: "application/json",
					success: function (data) {
						var div = "<div id='rooms'><div class='row'>";
						$.each(data, function (i, n) {
							// i=index 第幾個 n=element 元素
							div += "<div class='col-md-12 room'><div class='row'>";
							div += "<div class='col-md-6'><div class='room-img'>";
							if(n.picture){
								div += "<img src='"+ n.picture +"' width='100%'>";
							}else{
								div += "<img src='/morari/forum/img/nopicture.jpeg' width='100%' height='400px'>";
							}
							div += "</div></div>";
							div += "<div class='col-md-6'><div class='room-des'>";
							div += "<h1>" + n.title + "</h1>" + n.content + "<br><br>更新時間：" + n.releasedate + "<div class='room-link'><a href='/morari/forum/showpost.controller/" + n.postid + "'>查看貼文</a></div>";
							div += "</div></div>";
							div += "</div></div>";
						});
						div += "</div></div>";
						$("#camperpost").append(div);
					}
				});
                
            })            
        } 











    });