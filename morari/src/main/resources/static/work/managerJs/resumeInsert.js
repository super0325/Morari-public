// 載入 你的.html
$(document).ready(function() {
	// let uid;
	//	fetch("/morari/utils/getuid")
	//		.then(response => response.text())
	//		.then(data => {
	//		})

	var url = window.location.href;
	var uuid = url.split("/").pop();
	$('#uid').val(uuid);

	// 一鍵輸入 
	$("#fastinput").click(function() {
		fastinport();
	});

	$("#send").click(function() {
		function getFormData($form) {
			var unindexed_array = $form.serializeArray();
			var indexed_array = {};

			$.map(unindexed_array, function(n, i) {
				indexed_array[n['name']] = n['value'];
			});
			return indexed_array;
		}

		$.ajax({
			type: 'POST',
			url: '/morari/admin/resume/resumeInsert.controller/' + uuid,
			contentType: 'application/json',
			data: JSON.stringify(getFormData($("#insert"))),
			success: function(response) {
				$('#showInsert').empty("");
				$('#booking').remove();
				table =
					$('#showInsert').DataTable({
						"data": response,
						"columns":
							[

								{
									data: 'number',
									title: "履歷編號",
									responsivePriority: 1,
								},
								{
									data: 'userprofiles.uid',
									title: "會員編號",
									responsivePriority: 10,
								},

								{
									data: 'name',
									title: "姓名",
									responsivePriority: 5,
									"render": function (
										data, type,
										row, meta) {
											return "<p class=\"white-space nowrap\">"+data+"</p>"
										}
								},

								{
									data: 'age',
									title: "年次",
									responsivePriority: 2,
								},

								{
									data: 'gender',
									title: "性別",
									responsivePriority: 2,
								},

								{
									data: 'mail',
									title: "email",
									responsivePriority: 6,
								},

								{
									data: 'phone',
									title: "電話",
									responsivePriority: 4,
								},

								{
									data: 'educational',
									title: "學歷",
									responsivePriority: 7,
								},

								{
									data: 'skill',
									title: "專業技能",
									responsivePriority: 8,
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
					setTimeout(function () {
						
						table.responsive.recalc();
					}, 500);
			}
		});
	});
});
//				$('#su').empty("");
//				$('#booking').remove();
//				if (response == null) {
//					$('table').prepend("<tr><td colspan='2'>暫無資料</td></tr>");
//				} else {
//					var h3 = $('#su');
//					h3.prepend('成功送出');
//					var table = $('#showInsert');
//					table.append("<tr align='center'><th>會員編號</th><th>姓名</th><th>年次</th><th>性別</th><th>email</th><th>電話</th><th>學歷</th><th>專業技能</th></tr>");
//
//					var tr = "<tr align='center'>" +
//						"<td style='border: 1px solid #dddbdb;padding: 8px;text-align: center;background-color:#dddbdb'>" + response.userprofiles.uid + "</td>" +
//						"<td style='border: 1px solid #dddbdb;padding: 8px;text-align: center;background-color:#dddbdb'>" + response.name + "</td>" +
//						"<td style='border: 1px solid #dddbdb;padding: 8px;text-align: center;background-color:#dddbdb'>" + response.age + "</td>" +
//						"<td style='border: 1px solid #dddbdb;padding: 8px;text-align: center;background-color:#dddbdb'>" + response.gender + "</td>" +
//						"<td style='border: 1px solid #dddbdb;padding: 8px;text-align: center;background-color:#dddbdb'>" + response.mail + "</td>" +
//						"<td style='border: 1px solid #dddbdb;padding: 8px;text-align: center;background-color:#dddbdb'>" + response.phone + "</td>" +
//						"<td style='border: 1px solid #dddbdb;padding: 8px;text-align: center;background-color:#dddbdb'>" + response.educational + "</td>" +
//						"<td style='border: 1px solid #dddbdb;padding: 8px;text-align: center;background-color:#dddbdb'>" + response.skill + "</td></tr>";
//					table.append(tr);
//				}

function fastinport() {
	$("#name").val("張凱莉");
	$("#age").val("84");
	$("#mail").val("hcc8462@gmail.com");
	$("#phone").val("0955815651");
	$("#gender").attr('checked', true);
	$("#educational").val("屏東科技大學餐飲管理學系");
	$("#skill").val("多益700分，日文n3");

}