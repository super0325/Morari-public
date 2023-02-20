// 載入 你的.html
var uid;

$(document).ready(function() {

	fetch("/morari/utils/getuid")
		.then(response => response.text())
		.then(data => {
			// console.log(data)
			uid = data;
		})


	$.ajax({
		type: 'POST',
		url: '/morari/admin/work/jobShowAll.controller',
		contentType: 'application/json',
		success: function(data) {
			var banners = document.getElementById("banners");
			$.each(data, function(i, n) {
				//创建一个div
				var div = document.createElement("div");


				//设置div的id
				div.id = "abc";
				//设置div的class
				div.className = "col-md-3 col-sm-6";
				//设置style
				//div.style.display="block";
				div.click = "selectbyid()";
				//设置子div的其他属性，直接通过字符串连接的方式连接即可。动态值放在引号外面就行
				div.innerHTML = '<div id="' + n.rackid + '" class="item" style="cursor: pointer;">' +
					'<img  src="' + n.img + '" style="max-height: 130px;" alt="img">' +
					'<div class="homepage-slider-caption">' +
					'<h2>【' + n.job + '】</h2>' +
					'<h3>類型：' + n.type + '</h3>' +
					'<h3>地點：' + n.place + '</h3>' +
					'</div>' +
					'</div> ';

				banners.appendChild(div);
				(function(i) {
					var elementId = n.rackid;
					document.getElementById(elementId).addEventListener("click", function() {
						// 觸發控制器的代碼
						$.ajax({
							success: function() {
								window.location.href = "/morari/guest/work/workDetail.controller/" + elementId;
							}
						});
					});
				})(i);

			})
		}
	});
});

function selectJob(job) {
	$.ajax({
		type: 'POST',
		url: '/morari/guest/work/guestSelectLike.controller/' + job,
		dataType: 'json',
		success: function(data) {
			if (data.length == 0 || data == null) {
				$('#banners').empty();
				$('#showSelect').empty();
				$('#no').remove();
				$('table').prepend("<tr><td id='no'colspan='2'>暫無資料</td></tr>");
			} else {
				$('#banners').empty();
				$.each(data, function(i, n) {
					//创建一个div
					var div = document.createElement("div");
					//设置div的id
					div.id = "abc";
					//设置div的class
					div.className = "col-md-3 col-sm-6";
					//设置style
					//div.style.display="block";
					div.click = "selectbyid()";
					//设置子div的其他属性，直接通过字符串连接的方式连接即可。动态值放在引号外面就行
					div.innerHTML = '<div id="' + n.rackid + '" class="item" style="cursor: pointer;">' +
						'<img  src="' + n.img + '" style="max-height: 130px;" alt="img">' +
						'<div class="homepage-slider-caption">' +
						'<h2>【' + n.job + '】</h2>' +
						'<h3>類型：' + n.type + '</h3>' +
						'<h3>地點：' + n.place + '</h3>' +
						'</div>' +
						'</div>' +
						'</div> ';

					banners.appendChild(div);
					(function(i) {
						var elementId = n.rackid;
						document.getElementById(elementId).addEventListener("click", function() {
							// 觸發控制器的代碼
							$.ajax({
								success: function() {
									window.location.href = "/morari/guest/work/workDetail.controller/" + elementId;
								}
							});
						});
					})(i);

				})

			}
		}
	});
};
function selectType(type) {
	
	$.ajax({
		type: 'POST',
		url: '/morari/guest/work/selectTypeLike.controller/' + type,
		dataType: 'json',
		success: function(data) {
			if (data.length == 0 || data == null) {
				$('#banners').empty();
				$('#showSelect').empty();
				$('#no').remove();
				$('table').prepend("<tr><td id='no'colspan='2'>暫無資料</td></tr>");
			} else {
				$('#banners').empty();
				$.each(data, function(i, n) {
					//创建一个div
					var div = document.createElement("div");
					//设置div的id
					div.id = "abc";
					//设置div的class
					div.className = "col-md-3 col-sm-6";
					//设置style
					//div.style.display="block";
					div.click = "selectbyid()";
					//设置子div的其他属性，直接通过字符串连接的方式连接即可。动态值放在引号外面就行
					div.innerHTML = '<div id="' + n.rackid + '" class="item" style="cursor: pointer;">' +
						'<img  src="' + n.img + '" style="max-height: 130px;" alt="img">' +
						'<div class="homepage-slider-caption">' +
						'<h2>【' + n.job + '】</h2>' +
						'<h3>類型：' + n.type + '</h3>' +
						'<h3>地點：' + n.place + '</h3>' +
						'</div>' +
						'</div>' +
						'</div> ';

					banners.appendChild(div);
					(function(i) {
						var elementId = n.rackid;
						document.getElementById(elementId).addEventListener("click", function() {
							// 觸發控制器的代碼
							$.ajax({
								success: function() {
									window.location.href = "/morari/guest/work/workDetail.controller/" + elementId;
								}
							});
						});
					})(i);

				})

			}
		}
	});
};


