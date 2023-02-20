$(function () {
	let uid;
	fetch("/morari/utils/getuid")
		.then(response => response.text())
		.then(data => {
			uid = "/morari/showpostbyuserid.controller/" + data
		}).then(() => {
			$.ajax({
				url: uid,
				dataType: "JSON",
				contentType: "application/json",
				success: function (data) {
					if(data.length != 0){
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
							div += "<h1>" + n.title + "</h1>" + n.content + "<br><br>更新時間：" + n.releasedate + "<div class='room-link'><a href='/morari/forum/usershowpost.controller/" + n.postid + "'>查看貼文</a></div>";
							div += "</div></div>";
							div += "</div></div>";
						});
						div += "</div></div>";
						$("#userpost").append(div);
					}else{
						var div = "<div class='col-md-12 room'><div class='row'><div class='room-des col-md-12' style='text-align: center;'>沒有個人貼文</div></div></div>";
						$("#userpost").append(div);
					}	
				}
			});
		})
});