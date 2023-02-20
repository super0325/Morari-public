// 載入 你的.html
$(document).ready(function () {

	let uid;
	fetch("/morari/utils/getuid")
		.then(response => response.text())
		.then(data => {
			// console.log(data)
			uid = data;
		}).then(() => {
			//找營地名稱
			$("#campId").click(function () {

				$('#campName').empty("");
				$.ajax({
					type: 'POST',
					url: '/morari/admin/user/work/selectUUid.controller/' + uid,
					contentType: 'application/json',
					success: function (data) {
						$.each(data, function (i, n) {
							var table = $('#campName');
							var tr = "<tr align='center'>" +
								"<td>" + "<a style='color: 	#FF8C00;' href='/morari/admin/user/work/startInsert.controller/" + n.campID + "'>" + n.campName + "</td>" +
								"</tr>"
							table.append(tr);
						});
					}
				})

			})
			$.ajax({
				type: 'POST',
				url: '/morari/admin/user/work/userSelectUid.controller/' + uid,
				contentType: 'application/json',
				success: function (response) {
					$('#showAllJob').empty("");
					table =
						$('#showAllJob').DataTable({
							"data": response,
							"columns": [

								{
									data: 'rackid',
									title: "刊登編號",
									responsivePriority: 1,
									render: function (data, type, row) {
										return "<a style='color:#FF8C00' href='/morari/admin/user/resume/resumeStartCrud.controller/" + row.rackid + "'>" + row.rackid + '<i class="fa-solid fa-file"></i>'
									}
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
									"render": function (
										data, type,
										row, meta) {
											return "<p class=\"white-space nowrap\">"+data+"</p>"
										}
								},

								{
									data: 'job',
									title: "職缺",
									responsivePriority: 4,
									"render": function (
										data, type,
										row, meta) {
											return "<p class=\"white-space nowrap\">"+data+"</p>"
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
									"render": function (
										data, type,
										row, meta) {
											return "<p class=\"white-space nowrap\">"+data+"</p>"
										}
								},

								{
									data: 'time',
									title: "上班時段",
									responsivePriority: 7,
									"render": function (
										data, type,
										row, meta) {
											return "<p class=\"white-space nowrap\">"+data+"</p>"
										}
								},

								{
									data: 'rackup',
									title: "刊登時間",
									responsivePriority: 10,
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
									render: function (data, type, row) {
										return '<img width="300" src="' + data + '"/>';
									}
								},

								{
									data: null,
									title: "修改",
									responsivePriority: 1,
									render: function (data, type, row) {
										//									return '<button style="border:none;background-color:transparent" id="delete"  onclick="jobUpdate(' + row.rackid + ')"><a href="#" class="btn btn-warning btn-circle"><i class="fas fa-user-edit"></i></a></button>';
										return '<button class=\"datatable_edit_button\" onclick=\"jobUpdate(\'' + row.rackid + '\')\"><i class=\"fas fa-sliders-h\"></i></button>'

									}
								},

								{
									data: null,
									title: "刪除",
									responsivePriority: 1,
									render: function (data, type, row) {
										//									return '<button style="border:none;background-color:transparent" id="delete"  onclick="jobDelete(' + row.rackid + ')"><a href="#" class="btn btn-danger btn-circle"><i class="fas fa-trash-alt"></i></a></button>';
										return '<button class=\"datatable_del_button\"   onclick=\"userDelete	(\'' + row.rackid + '\')\"><i class=\"fas fa-trash-alt\"></i></button>'
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
					$('#showAllJob thead tr th').css('white-space', 'nowrap');
					table.responsive.recalc();
					setTimeout(function () {
						table.responsive.recalc();
					}, 500);
				}
			});
		})
});
function userDelete(rackid) {
	if (confirm("確定刪除該筆資料(刊登編號:" + rackid + ")?")) {
		console.log(rackid)
		$.ajax({
			type: 'GET',
			url: '/morari/admin/user/work/userDelete.controller/' + rackid,
			dataType: 'json',
			success: function (data) {
				
				
				if (data) {
					if (confirm("該職缺有人投遞簡歷確定刪除嗎?")) {
						$.ajax({
							type: 'delete',
							url: '/morari/admin/user/work/userTrueDelete.controller/' + rackid,
							dataType: 'text',
							success: function (data) {
								console.log(data)
								alert(data);
								location.reload();
							}

						})
					}
				} else {
					console.log('123123131')
					$.ajax({
						type: 'delete',
						url: '/morari/admin/user/work/userTrueDelete.controller/' + rackid,
						contentType: 'application/json',
						success: function (data) {
							alert(data);
							location.reload();
						}
					})
				}
			}
		})
	}
}
function jobUpdate(rackid) {
	window.location.href = '/morari/admin/user/work/startUpdate.controller/' + rackid

};