// 顯示畫面
$(function () {
	var columns = [];
	$.ajax({
		url: "/morari/showmaxoption.controller",
		dataType: "JSON",
		contentType: "application/json",
		success: function (data) {
			columns.push({ data: "voteid", title: "投票編號" });
			columns.push({ data: "votename", title: "投票題目" });
			for (let i = 1; i <= data; i++) {
				columns.push({
					title: "選項" + i,
					render: function (data, type, row) {
						let option = ""
						let voteoption = row.voteoption;
						if (voteoption[i]) {
							option = voteoption[i];
						}
						return option;
					}
				});
			}
			columns.push({
				title: "中獎者",
				render: function (data, type, row) {
					let winner = ""
					let user = row.email;
					if (user) {
						winner = user;
					}
					return winner;
				}
			});
			columns.push({
				data: "voteid", title: "投票狀態", render: function (data, type, row) {
					if (row.voting == 1) {
						return '<select id="changevotingid' + row.voteid + '" onchange="changevoting(' + row.voteid + ')"><option value="1" selected>投票中</option><option value="2">已結束</option><option value="0">隱藏</option></select>';
					} else if (row.voting == 2) {
						return '<select id="changevotingid' + row.voteid + '" onchange="changevoting(' + row.voteid + ')"><option value="1">投票中</option><option value="2" selected>已結束</option><option value="0">隱藏</option></select>';
					} else {
						return '<select id="changevotingid' + row.voteid + '" onchange="changevoting(' + row.voteid + ')"><option value="1">投票中</option><option value="2">已結束</option><option value="0" selected>隱藏</option></select>';
					}
				}
			});
			columns.push({
				data: "voteid", title: "查看投票紀錄",
				render: function (data, type, row) {
					return '<button class="my-button datatable_search_button" onclick=\"window.location.href=\'/morari/admin/forum/showvoterecordadmin.controller/' + row.voteid + '\'\"><i class=\"fas fa-search-plus\"></i></button>';
				}
			});
			columns.push({
				data: "voteid", title: "抽獎",
				render: function (data, type, row) {
					if(row.voting != 1 && row.email == null){
						return '<button class="my-button datatable_lottery_button" onclick="lottery('+ row.voteid + ')"><i class=\"fas fa-gift\"></i></button>';
					}
					return "";
				}
			});

			$('#allvote').DataTable({
				"ajax": {
					url: "/morari/showvoteandvoteoption.controller",
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
				"columns": columns
			});

		}
	});


});

function lottery(id){
	if (confirm("是否要進行抽獎?")) {
		$.ajax({
			type: "put",
			url: "/morari/updatewinner.controller/" + id,
			dataType: "JSON",
			contentType: "application/json",
			success: function (data) {
				if (data == true) {
					alert("抽獎完成並發送email")
					location.reload();
				}else{
					alert("無人投票，無法抽獎");
				}
			}
		});
	}
}

function changevoting(id) {
	let selectedValue = $("#changevotingid" + id).val();
	if (selectedValue == 2) {
		if (confirm("是否要結束投票?")) {

			$.ajax({
				type: "put",
				url: "/morari/endvote.controller/" + id,
				dataType: "JSON",
				contentType: "application/json",
				success: function (data) {
					if (data == true) {
						alert("結束投票成功");
						location.reload();
					}
				}
			});
		}else{
			location.reload();
		}
	} else if (selectedValue == 0) {
		if (confirm("是否要隱藏投票?")) {

			$.ajax({
				type: "put",
				url: "/morari/hidevote.controller/" + id,
				dataType: "JSON",
				contentType: "application/json",
				success: function (data) {
					if (data == true) {
						alert("隱藏投票成功");
						location.reload();
					}
				}
			});
		}else{
			location.reload();
		}
	} else {
		if (confirm("是否要進行投票?")) {

			$.ajax({
				type: "put",
				url: "/morari/cancelhidevote.controller/" + id,
				dataType: "JSON",
				contentType: "application/json",
				success: function (data) {
					if (data == true) {
						alert("進行投票成功");
						location.reload();
					}
				}
			});
		}else{
			location.reload();
		}
	}
}