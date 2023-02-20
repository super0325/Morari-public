// 載入 你的.html
$(document).ready(function() {

	//找到營地的值塞入
	var url = window.location.href;
	var campid = url.split("/").pop();

	// 一鍵輸入 
	$("#fastinput").click(function() {
		fastinport();
	});

	$.ajax({
		type: 'POST',
		url: '/morari/admin/user/work/selectCampid.controller/' + campid,
		dataType: 'json',
		success: function(data) {
			$('#uid').val(data.userprofiles.uid);
			$('#1').val(data.campName);
			$('#2').val(data.city.cityName + data.location);
			$('#3').val(data.campPicturesPath);

		}
	});


	//新增職缺
	$("#send").click(function() {

		//  var uid =document.getElementById('uid')
		function getFormData($form) {
			var unindexed_array = $form.serializeArray();
			var indexed_array = {};

			$.map(unindexed_array, function(n, i) {
				indexed_array[n['name']] = n['value'];
			});

			return indexed_array;
		}
		var uuid = document.getElementById("uid").value;
		
		$.ajax({
			type: 'POST',
			url: '/morari/admin/work/jobInsert.controller/' + uuid,
			contentType: 'application/json',
			data: JSON.stringify(getFormData($("#insert"))),
			dataType:'json',
			success: function(response) {
				$('#showInsert').empty("");
				$('#insert').empty("");
				console.log(response);
				table =
					$('#showInsert').DataTable({
				
						"data": response,
						"columns": [

							{
								data: 'rackid',
								title: "刊登編號",
								responsivePriority: 1,
							},
							{
								data: 'userprofiles.uid',
								title: "會員編號",
								responsivePriority: 12,
							},

							{
								data: 'campname',
								title: "營區",
								responsivePriority: 2,
							},

							{
								data: 'place',
								title: "地點",
								responsivePriority: 8,
							},
							{
								data: 'type',
								title: "類型",
								responsivePriority: 5,
								"render": function(
									data, type,
									row, meta) {
									return "<p class=\"white-space nowrap\">" + data + "</p>"
								}
							},

							{
								data: 'job',
								title: "職缺",
								responsivePriority: 4,
								"render": function(
									data, type,
									row, meta) {
									return "<p class=\"white-space nowrap\">" + data + "</p>"
								}
							},

							{
								data: 'salary',
								title: "薪資",
								responsivePriority: 3,
							},

							{
								data: 'quantity',
								title: "人數",
								responsivePriority: 2,
							},

							{
								data: 'date',
								title: "上班日期",
								responsivePriority: 6,
							},

							{
								data: 'time',
								title: "上班時段",
								responsivePriority: 7,
							},

							{
								data: 'rackup',
								title: "刊登時間",
								// 'white-space', 'nowrap'
								responsivePriority: 10,
								"render": function(
									data, type,
									row, meta) {
									return "<p class=\"white-space nowrap\">" + data + "</p>"
								}
							},

							{
								data: 'remark',
								title: "備註",
								responsivePriority: 15,
							},

							{
								data: 'img',
								title: "照片",
								responsivePriority: 13,
								render: function(data, type, row) {
									return '<img width="300" src="' + data + '"/>';
								}
							},
						],
						"responsive": true,
						lengthMenu: [5, 10, 15, 20],
						language: {
							"lengthMenu": "顯示_MENU_ 筆資料",
							"info": "第 _START_ 至 _END_ 筆資料，共 _TOTAL_ 筆",
							"search": "搜尋：",
							"paginate": {
								"previous": " 上一頁__",
								"next": "__下一頁"
							}
						}
					});
				// 表頭不換行
				$('#showInsert thead tr th').css('white-space', 'nowrap');
				table.responsive.recalc();
				setTimeout(function() {
					table.responsive.recalc();
				}, 500);
			}
		});
	});
});
//				$('#su').empty("");
//				$('#insert').empty("");
//				$('#img').empty("");
//				if (response == null) {
//					$('table').prepend("<tr><td colspan='2'>暫無資料</td></tr>");
//				} else {
//					var h3 = $('#su');
//					h3.prepend('新增成功');
//					var table = $('#showInsert');
//					table.append("<tr align='center'><th>會員編號</th><th>刊登編號</th><th>營區</th><th>地點</th><th>類型</th></th><th>職缺</th><th>薪資</th><th>人數</th><th>上班日期</th><th>上班時段</th><th>刊登時間</th><th>備註</th><th>照片</th></tr>");
//
//					var tr = "<tr>" +
//						"<td style='border: 1px solid #f2f2f2;padding: 8px;text-align: center;background-color:#dddbdb '>" + response.userprofiles.uid + "</td>" +
//						"<td style='border: 1px solid #f2f2f2;padding: 8px;text-align: center;background-color:#dddbdb '>" + response.rackid + "</td>" +
//						"<td style='border: 1px solid #f2f2f2;padding: 8px;text-align: center;background-color:#dddbdb '>" + response.campname + "</td>" +
//						"<td style='border: 1px solid #f2f2f2;padding: 8px;text-align: center;background-color:#dddbdb '>" + response.place + "</td>" +
//						"<td style='border: 1px solid #f2f2f2;padding: 8px;text-align: center;background-color:#dddbdb '>" + response.type + "</td>" +
//						"<td style='border: 1px solid #f2f2f2;padding: 8px;text-align: center;background-color:#dddbdb '>" + response.job + "</td>" +
//						"<td style='border: 1px solid #f2f2f2;padding: 8px;text-align: center;background-color:#dddbdb '>" + response.salary + "</td>" +
//						"<td style='border: 1px solid #f2f2f2;padding: 8px;text-align: center;background-color:#dddbdb '>" + response.quantity + "</td>" +
//						"<td style='border: 1px solid #f2f2f2;padding: 8px;text-align: center;background-color:#dddbdb '>" + response.date + "</td>" +
//						"<td style='border: 1px solid #f2f2f2;padding: 8px;text-align: center;background-color:#dddbdb '>" + response.time + "</td>" +
//						"<td style='border: 1px solid #f2f2f2;padding: 8px;text-align: center;background-color:#dddbdb '>" + response.rackup + "</td>" +
//						"<td style='border: 1px solid #f2f2f2;padding: 8px;text-align: center;background-color:#dddbdb '>" + response.remark + "</td>" +
//						"<td><img width='120px' height='120px' src='" + response.img + "'></td>" + "</tr>";
//					table.append(tr);
//				}

function fastinport() {
	$("#type").val("長期工作");
	$("#job").val("房務員");
	$("#salary").val("36000");
	$("#quantity").val("1");
	$("#date").val("隨時");
	$("#time").val("10-19");
	$("#remark").val("細心、負責，交辦事項能完成");

}