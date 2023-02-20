// 顯示畫面
$(function () {

	// voteid
	let url = window.location.href;
	let arr = url.split('/');
	let voteid = arr[arr.length - 1];

	$('#allvoterecord').DataTable({	
		"ajax": {
			url: "/morari/showvoteoptionandvoterecordbyvoteid.controller/" + voteid,
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
		"columns":
		 [
		 	{ data: "optionid", title: "選項編號" },
		 	{ data: "optionname", title: "選項名稱" },
		 	{ data: "recordcount", title: "投票人數" }
		 ]
	});

});
