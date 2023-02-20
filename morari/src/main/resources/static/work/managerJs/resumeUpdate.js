// 載入 你的.html

$(document).ready(function() {
	var url = window.location.href;
	var id = url.split("/").pop();

	$.ajax({
		type: 'POST',
		url: '/morari/admin/resume/selectNumber.controller/' + id,
		dataType: 'json',
		success: function(data) {
			$('#1').val(data.userprofiles.uid);
			$('#2').val(data.name);
			$('#3').val(data.age);
			$('#6').val(data.mail);
			$('#7').val(data.phone);
			$('#8').val(data.educational);
			$('#9').val(data.skill);

			switch (data.gender) {
				case '男':
					$("#4").attr('checked', true)
					break;
				case '女':
					$("#5").attr('checked', true)
					break;
			}
		}
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
			type: 'Put',
			url: '/morari/admin/resume/resumeUpdate.controller/' + id,
			contentType: 'application/json',
			data: JSON.stringify(getFormData($("#update"))),
			success: function(response) {
				$('#showUpdate').empty("");
				$('#booking').remove();
				table =
					$('#showUpdate').DataTable({
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
					$('#showUpdate thead tr th').css('white-space', 'nowrap');
					table.responsive.recalc();
					setTimeout(function () {
						
						table.responsive.recalc();
					}, 500);
			}
		});
	});
});

//				$('#showInsert').empty("");
//				$('#su').empty("");
//				$('#booking').remove();
//				$('#img').empty("");
//				if (response == null) {
//					$('table').prepend("<tr><td colspan='2'>暫無資料</td></tr>");
//				} else {
//					var h3 = $('#su');
//					h3.prepend('修改成功');
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
