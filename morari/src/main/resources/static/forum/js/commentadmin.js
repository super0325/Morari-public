var table;

// 顯示畫面
$(function(){
	// postid
	let url = window.location.href;
	let arr = url.split('/');
	let postid = arr[arr.length - 1];
	
	table = $('#postcomment').DataTable({
		"ajax":{
			url:"/morari/showpostcommentbypostid.controller/" + postid,
			dataSrc:""
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
		"columns":[
			{data:"postcommentid", title:"留言編號", responsivePriority: 2},
			{data:"uid", title:"會員編號", responsivePriority: 8},
			{data:"postcomment", title:"留言內容", responsivePriority: 9},
			{data:"postcommentreport", title:"是否檢舉留言", responsivePriority: 3,
				render: function(data,type,row) {
					console.log(row);
					if (row.postcommentreport == 0) {
						return "否";
					}else{
						return "是";
					}
				}
			},
			{
			    data:"informantuid", title: "檢舉者", responsivePriority: 4,
			    render: function(data,type,row) {
			    	if(row.postcommentreport == 0){
						return "";
					}else{
						return row.informantuid;
					}
			    }
  			},
			{
				data:"postcommenthide", title:"是否隱藏留言", responsivePriority: 6,
				render: function(data,type,row) {
					if (row.postcommenthide == 0) {
							return "否";
					}else{
						return "是";
					}
				}
			},
  			{
			    data:"postcommenthide", title: "隱藏/取消隱藏留言", responsivePriority: 7,
			    render: function(data,type,row) {
			      	if(row.postcommenthide == 0){
			      		return '<button class="my-button datatable_hide_button" onclick="hidepostcomment('+ row.postcommentid +')"><i class=\"fas fa-eye-slash\"></i></button>';
			      	}else{
						return '<button class="my-button datatable_hide_button" onclick="cancelhidepostcomment('+ row.postcommentid +')"><i class=\"fas fa-eye\"></i></button>';
					}
			    }
  			},
  			{
			    data:"postcommenthide", title: "取消檢舉留言", responsivePriority: 5,
			    render: function(data,type,row) {
			    	if(row.postcommentreport == 1){
						return '<button class="my-button datatable_report_button" onclick="cancelreportpostcomment(\'' + row.postcommentid + '\',\'' + row.informantuid + '\')"><i class=\"fas fa-bell-slash\"></i></button>';
					}
					return null;
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
	$('#postcomment select').on('change', function () {
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

function hidepostcomment(id){
	if (confirm("是否確定隱藏留言?")) {
		$.ajax({
			type:"put",
			url:"/morari/hidepostcomment.controller/" + id,
			dataType:"JSON",
			contentType:"application/json",
			success: function(data){
				if(data == true){
					alert("隱藏留言成功");
					location.reload();
				}
			}
		});
	}
}
	
function cancelhidepostcomment(id){
	if (confirm("是否確定取消隱藏留言?")) {
		$.ajax({
			type:"put",
			url:"/morari/cancelhidepostcomment.controller/" + id,
			dataType:"JSON",
			contentType:"application/json",
			success: function(data){
				if(data == true){
					alert("取消隱藏留言成功");
					location.reload();
				}
			}
		});
	}
}

function cancelreportpostcomment(id, informantuid){
	if (confirm("是否確定取消檢舉留言?")) {
		$.ajax({
			type:"put",
			url:"/morari/cancelreportpostcomment.controller/" + id,
			dataType:"JSON",
			contentType:"application/json",
			success: function(data){
				if(data == true){
					alert("取消檢舉留言成功");
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