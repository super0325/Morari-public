// 載入 你的.html
$(document).ready(function() {
	var url = window.location.href;
	var rackid = url.split("/").pop();
	let uid;
	fetch("/morari/utils/getuid")
		.then(response => response.text())
		.then(data => {
			uid = data;
		}).then(() => {
			$.ajax({
				type: 'Post',
				url: '/morari/admin/user/resume/userResumeRackId.controller/' + rackid,
				contentType: 'application/json',
				success: function(response) {
console.log(response);
					$('#showResume').empty("");
					table =
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
									title: "寄信",
									responsivePriority: 1,
									render: function(data, type, row) {
										return "<button  class='btn btn-success btn-icon-split' onclick='mailInsert(" + row.number + ")'>mail通知面試</button>";
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
							$('#showAll_wrapper thead tr th').css('white-space', 'nowrap');
							table.responsive.recalc();
							setTimeout(function () {
								table.responsive.recalc();
							}, 500);
				}
			});
		});
});

//			if (response == null || response.length == 0) {
//				var h2 = $('#re');
//				h2.prepend('投遞履歷結果');
//				$('table').prepend("<tr><td colspan='2'>暫無資料</td></tr>");
//			} else {
//				var h2 = $('#re');
//				h2.prepend('投遞履歷結果');
//				var table = $('#showInsert');
//				table.append("<tr><th>會員編號</th><th>姓名</th><th>年次</th><th>性別</th><th>email</th><th>電話</th><th>學歷</th><th>專業技能</th></tr>");
//
//				$.each(response, function(i, n) {
//					var tr = "<tr align='center'>" +
//						"<td>" + n.userprofiles.uid + "</td>" +
//						"<td>" + n.name + "</td>" +
//						"<td>" + n.age + "</td>" +
//						"<td>" + n.gender + "</td>" +
//						"<td>" + n.mail + "</td>" +
//						"<td>" + n.phone + "</td>" +
//						"<td>" + n.educational + "</td>" +
//						"<td>" + n.skill + "</td>" +
//						"<td><button class='mail' onclick='mailInsert(" + n.number + ")'></button></td>" + "</tr>";
//					table.append(tr);
//				});
//			}

function mailInsert(number) {
	window.location.href = '/morari/admin/user/work/startMail.controller/' + number
}

