// 載入 你的.html

$(document).ready(function() {

	let uid;
	fetch("/morari/utils/getuid")
		.then(response => response.text())
		.then(data => {
			// console.log(data)
			uid = data;
		}).then(() => {

			$("#campId").click(function() {

				$('#campID').empty("");
				var div = $('#campID');
				var input = "<form>請輸入會員編號<input type='text' id='uuid'><input class='btn btn-success btn-icon-split 'type='button' value='新增' onclick='submitForm()'></form>";
				div.append(input);
			})

			$.ajax({
				type: 'POST',
				url: '/morari/admin/resume/resumeShowAll.controller',
				contentType: 'application/json',
				success: function(response) {
					table =
					$('#showResume').empty("");
					$('#showResume').DataTable({
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

								{
									data: null,
									title: "修改",
									responsivePriority: 1,
									render: function(data, type, row) {
										//										return '<button style="border:none;background-color:transparent" id="delete"  onclick="resumeUpdate(' + row.number + ')"><a href="#" class="btn btn-warning btn-circle"><i class="fas fa-user-edit"></i></a></button>';
										return '<button class=\"datatable_edit_button\" onclick=\"resumeUpdate(\'' + row.number + '\')\"><i class=\"fas fa-sliders-h\"></i></button>'
									}
								},
								{
									data: null,
									title: "刪除",
									responsivePriority: 1,
									render: function(data, type, row) {
										//										return '<button style="border:none;background-color:transparent" id="delete"  onclick="resumeDelete(' + row.number + ')"><a href="#" class="btn btn-danger btn-circle"><i class="fas fa-trash-alt"></i></a></button>';
										return '<button class=\"datatable_del_button\"   onclick=\"resumeDelete(\'' + row.number + '\')\"><i class=\"fas fa-trash-alt\"></i></button>'
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
					$('#showResume thead tr th').css('white-space', 'nowrap');
					table.responsive.recalc();
					setTimeout(function () {
						
						table.responsive.recalc();
					}, 500);
				}
			});
		});
});

function resumeDelete(number) {
	if (confirm("確定刪除該筆資料(履歷編號:" + number + ")?")) {
		$.ajax({
			type: 'delete',
			url: '/morari/admin/resume/resumeDelete.controller/' + number,
			dataType: 'TEXT',
			success: function(data) {
				alert(data);
				location.reload();
			}
		});
	} else {
	}
};

function resumeUpdate(number) {
	window.location.href = '/morari/admin/resume/update.controller/' + number

};

// 新增履歷 透過會員編號確定是否有無寫過
function submitForm() {
	var input1 = document.getElementById("uuid").value;
	$.ajax({
		type: 'POST',
		url: '/morari/admin/resume/resumeSelectUid.controller/' + input1,
		contentType: 'application/json',
		success: function(data) {

			if (data == null || data.length == 0) {

				window.location.href = '/morari/admin/resume/startResumeInsert.controller/' + input1

			} else {
				alert("該會員已填寫過履歷");
			}

		}
	});

}




