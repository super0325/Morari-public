// 顯示畫面

var table
$(document)
	.ready(
		function () {
			table = $('#allpost')
				.DataTable({
					"ajax": {
						url: "/morari/showall.controller",
						dataSrc: ""
					},
					lengthMenu: [5, 10, 15, 20],
					language: {
						"lengthMenu": "顯示 _MENU_ 筆資料",
						"info": "顯示第 _START_ 至 _END_ 筆資料，共 _TOTAL_ 筆",
						"search": "搜尋：",
						"paginate": {
							"next": "下一頁",
							"previous": "上一頁",
							"first": "首頁",
							"last": "末頁",
						}
					},
					"pagingType": "full_numbers",
					"columns": [
						{ data: "postid", title: "貼文編號", responsivePriority: 2 },
						{ data: "uid", title: "會員編號", responsivePriority: 20 },
						{ data: "title", title: "標題", responsivePriority: 3 },
						{ data: "content", title: "內容", responsivePriority: 22 },
						{
							data: "picture", title: "照片", responsivePriority: 21,
							render: function (data, type, row) {
								if (data != null) {
									return '<img src=\"' + data + '\" width="300"></img>';
								} return null;
							}
						},
						{ data: "people", title: "人數", responsivePriority: 14 },
						{ data: "price", title: "價錢", responsivePriority: 15 },
						{
							data: "county", title: "縣市", responsivePriority: 16,
							render: function (data, type, row) {
								var countyInner = ["台北市", "新北市", "基隆市", "桃園市", "新竹縣", "新竹市", "苗栗縣", "台中市", "彰化縣", "南投縣",
									"雲林縣", "嘉義縣", "嘉義市", "台南市", "高雄市", "屏東縣", "宜蘭縣", "花蓮縣", "台東縣", "澎湖縣", "金門縣", "連江縣"];
								var countyValue = ["TPE", "TPH", "KLU", "TYC", "HSH", "HSC", "MAL", "TXG", "CWH", "NTO", "YLH", "CHY",
									"CYI", "TNN", "KHH", "IUH", "ILN", "HWA", "TTT", "PEH", "KMN", "LNN"];
								for (let i = 0; i < countyValue.length; i++) {
									if (countyValue[i] == row.county) {
										return countyInner[i];
									} 
								}
								return null;
							}
						},
						{ data: "startdate", title: "起始日期", responsivePriority: 18 },
						{ data: "enddate", title: "結束日期", responsivePriority: 19 },
						{ data: "score", title: "評分", responsivePriority: 17 },
						{ data: "releasedate", title: "更新日期", responsivePriority: 11 },
						{ data: "userlike", title: "喜歡人數", responsivePriority: 12 },
						{ data: "userunlike", title: "不喜歡人數", responsivePriority: 13 },
						{
							data: "postreport", title: "是否檢舉貼文", responsivePriority: 7,
							render: function (data, type, row) {
								if (row.postreport == 0) {
									return "否";
								} else {
									return "是";
								}
							}
						},
						{
							data: "informantuid", title: "檢舉者", responsivePriority: 8,
							render: function (data, type, row) {
								if (row.postreport == 0) {
									return "";
								} else {
									return row.informantuid;
								}
							}
						},
						{
							data: "posthide", title: "是否隱藏貼文", responsivePriority: 5,
							render: function (data, type, row) {
								if (row.posthide == 0) {
									return "否";
								} else {
									return "是";
								}
							}
						},
						{
							data: "postid", title: "修改貼文", responsivePriority: 4,
							render: function (data, type, row) {
								return '<button class="my-button datatable_edit_button" onclick=\"window.location.href=\'/morari/admin/forum/showupdateadmin.controller/' + row.postid + '\'\"><i class=\"fas fa-sliders-h\"></i></button>';
							}
						},
						{
							data: "postid", title: "隱藏/取消隱藏貼文", responsivePriority: 6,
							render: function (data, type, row) {
								if (row.posthide == 0) {
									return '<button class="my-button datatable_hide_button" onclick="hidepost(' + row.postid + ')"><i class=\"fas fa-eye-slash\"></i></button>';
								} else {
									return '<button class="my-button datatable_hide_button" onclick="cancelhidepost(' + row.postid + ')"><i class=\"fas fa-eye\"></i></button>';
								}
							}
						},
						{
							data: "postid", title: "取消檢舉貼文", responsivePriority: 9,
							render: function (data, type, row) {
								if (row.postreport == 1) {
									return '<button class="my-button datatable_report_button" onclick="cancelreportpost(\'' + row.postid + '\',\'' + row.informantuid + '\')"><i class="fas fa-bell-slash"></i></button>';
								}
								return null;
							}
						},
						{
							data: "postid", title: "查看留言", responsivePriority: 10,
							render: function (data, type, row) {
								return '<button class="my-button datatable_search_button" onclick=\"window.location.href=\'/morari/admin/forum/showcommentadmin.controller/' + row.postid + '\'\"><i class=\"fas fa-search-plus\"></i></button>';
							}
						}
					],
					"paging": true,
					"searching": true,
					"responsive": true,
				});
			// 翻頁響應刷新
			$('.dataTables_paginate').on('click', function () {
				table.responsive.recalc();
				setTimeout(function () {
					table.responsive.recalc();
				}, 500);
			});
			// 頁面顯示響應刷新
			$('#allpost select').on('change', function () {
				table.responsive.recalc();
				setTimeout(function () {
					table.responsive.recalc();
				}, 500);
			});
			// 響應刷新
			setTimeout(function () {
				table.responsive.recalc();
			}, 500);
		});


function hidepost(id) {
	if (confirm("是否確定隱藏?")) {
		$.ajax({
			type: "put",
			url: "/morari/hidepost.controller/" + id,
			dataType: "JSON",
			contentType: "application/json",
			success: function (data) {
				if (data == true) {
					alert("隱藏成功")
					location.reload();
				}
			}
		});
	}
}

function cancelhidepost(id) {
	if (confirm("是否確定取消隱藏?")) {
		$.ajax({
			type: "put",
			url: "/morari/cancelhidepost.controller/" + id,
			dataType: "JSON",
			contentType: "application/json",
			success: function (data) {
				if (data == true) {
					alert("取消隱藏成功");
					location.reload();
				}
			}
		});
	}
}

function cancelreportpost(id, informantuid) {
	if (confirm("是否確定取消檢舉?")) {
		$.ajax({
			type: "put",
			url: "/morari/cancelreportpost.controller/" + id,
			dataType: "JSON",
			contentType: "application/json",
			success: function (data) {
				if (data == true) {
					alert("取消檢舉成功");
					if (confirm("是否封鎖檢舉者?")) {
						$.ajax({
							type: "put",
							url: "/morari/lockaccount.controller/" + informantuid,
							dataType: "JSON",
							contentType: "application/json",
							success: function (data) {
								if (data == true) {
									alert("封鎖檢舉者成功");
									location.reload();
								}
							}
						});
					}else{
						location.reload();
					}
				}
			}
		});
	}
}