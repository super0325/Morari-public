$(function () {
	$.ajax({
		url: "/morari/shownonhidepost.controller",
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
			$("#allpost").append(div);
		}
	});
});