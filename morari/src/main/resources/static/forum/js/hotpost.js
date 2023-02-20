fetch("/morari/forum/html/hotpost.html")
    .then(response => response.text())
    .then(html => {
        // 將載入的 HTML 放入 .footer 元素中
        document.querySelector(".hotpost").innerHTML = html;
		
		$(function () {
			$.ajax({
				url: "/morari/showhotpost.controller",
				dataType: "JSON",
				contentType: "application/json",
				success: function (data) {
					if(data.picture){
						$("#hotimage").attr({"src": data.picture, "width": "100%"});
					}else{
						$("#hotimage").attr({"src": "/morari/forum/img/nopicture.jpeg", "width": "100%", "height": "400px"});
					}
					$("#hottitle").html(data.title);
					$("#hotcontent").html(data.content);
					$("#hotreleasedate").html("更新時間:" + data.releasedate);
					$("#hotshow").attr("href", "/morari/forum/showpost.controller/" + data.postid);
				}
			});
		});
	});