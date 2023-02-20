// 載入 你的.html
//fetch("work/html/test.html")
//    .then(response => response.text())
//    .then(html => {
//        // 將載入的 HTML 放入 .footer 元素中
//        document.querySelector(".test").innerHTML = html;
//    });
var uid;

$(document).ready(function() {

	fetch("/morari/utils/getuid")
		.then(response => response.text())
		.then(data => {
			uid = data;
		})
	var url = window.location.href;
	var rackid = url.split("/").pop();

	$.ajax({
		type: 'POST',
		url: '/morari/guest/work/guestSelectRackId.controller/' + rackid,
		contentType: 'application/json',
		success: function(data) {
			var banners = document.getElementById("banners");
			//创建一个div
			var div = document.createElement("div");
			//设置div的id
			// 					div.id = "abc";
			//设置div的class
			div.className = "col-md-12 room";
			//设置style
			//div.style.display="block";
			//div.click = "selectbyid()";
			//设置子div的其他属性，直接通过字符串连接的方式连接即可。动态值放在引号外面就行
			div.innerHTML =

				'<div class="row">' +
				'<div class="col-md-6">' +
				'<div class="room-img">' +
				'<img src="' + data.img + '"  style="width:500px;max-height:400px;cursor: pointer;">' +
				'</div>' +
				'</div>' +
				'<div class="col-md-6">' +
//				'<div class="room-des">' +
				'<h1>' + data.job + '</h1>' +
				'<h3>◇薪水：' + data.salary + '</h3>' +
				'<h3>◇類型：' + data.type + '</h3>' +
				'<ul class="room-size">' +
				'<li>營區： ' + data.campname + '</li>' +'<br>' +
				'<li>地點：' + data.place + ' </li>' + '<br>' +
				'<li>上班日期： ' + data.date + ' </li>' +'<br>' +
				'<li>上班時段： ' + data.time + ' </li>' + '<br>' +
				'<li>人數： ' + data.quantity + ' </li>' +'<br>' +
				'<li>備註： ' + data.remark + ' </li>' +
				'</ul>' +

				'<div class="room-link">' +
				'<a href="#" data-toggle="modal" data-target="#modal-id" onclick="mailResume(' + data.rackid + ')">我要應徵</a>' +
				'</div>' +
				'</div>' +
				'</div>' +
				'</div>';

			banners.appendChild(div);
		}
	})

})
function mailResume(rackid) {
	$.ajax({
		type: 'POST',
		url: '/morari/guest/work/applyJob.controller/' + uid + '/' + rackid,
		dataType: 'TEXT',
		success: function(data) {
			if (data == 'false') {
				if (confirm("尚未填寫履歷！是否要前往填寫？")) {
					window.location.href = '/morari/guest/work/startResumeInsert.controller';
				}
			} else {
				if (confirm("確定應徵嗎?")) {
					alert("完成應徵囉，靜待佳音~")
				window.location.href = '/morari/guest/work/workGuest.controller';
				}
			}
		}
	});
}