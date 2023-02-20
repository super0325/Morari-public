var receivedData;
var file;
var edituid;
var editin;
fetch("/morari/camper/html/usertable.html")
	.then(response => response.text())
	.then(html => {
		// 將載入的 HTML 放入 .footer 元素中
		document.querySelector(".usertablediv").innerHTML = html;
		var table
		$(document)
			.ready(
				function () {
					table = $('#memberlist')
						.DataTable(
							{
								"ajax": {
									"url": "/morari/admin/camper/api/showall",
									"type": "GET",
									// 發送請求
									"dataSrc": function (data) {
										receivedData = data;
										return data;
									}
								},
								"lengthChange": true,
								"lengthMenu": [[5, 10, 20, 50, -1], [5, 10, 20, 50, "All"]],
								"paging": true,
								"searching": true,
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
									{
										"data": 'accountnonlocked'
										, "title": "鎖定"
										, "render": function (
											data, type,
											row, meta) {
											if (data) {
												return '<input type="button" class="user_edit_button_ture" onclick="accountlocked(\'' + meta.row + '\')" value="未鎖定">'
											} else {
												return '<input type="button" class="user_edit_button_false" onclick="accountlocked(\'' + meta.row + '\')" value="已封鎖">'
											}
										}
										, responsivePriority: 16
									},
									{
										"data": 'uid'
										, "title": "ID"
										, "render": function (
											data, type,
											row, meta) {
											return data
										}
										, responsivePriority: 40
									},
									{
										"data": 'shot'
										, "title": "大頭貼"
										, responsivePriority: 60
										, "render": function (
											data, type,
											row, meta) {
											if (data != null) {
												return '<img src=\"' + data + '\" alt=\"shot\" ></img>';
											}
											return " ";
										}
									},
									// {
									// 	"data": 'isenabled'
									// 	, "title": "啟用"
									// 	, "render": function (
									// 		data, type,
									// 		row, meta) {
									// 		if (data) {
									// 			return '<input type="button" class="user_edit_button_ture" onclick="enabled(\'' + meta.row + '\')" value="已啟用">'
									// 		} else {
									// 			return '<input type="button" class="user_edit_button_false" onclick="enabled(\'' + meta.row + '\')" value="未啟用">'
									// 		}
									// 	}
									// 	, responsivePriority: 15
									// },

									{
										"data": 'email'
										, "title": "電子信箱"
										, responsivePriority: 10
									},
									{
										"data": 'nickname'
										, "title": "暱稱"
										, responsivePriority: 30

									},
									{
										"data": 'firstname'
										, "title": "名字"
									},
									{
										"data": 'lastname'
										, "title": "姓氏"
									},
									{
										"data": 'phone'
										, "title": "電話"
									},
									{
										"data": 'roles'
										, "title": "權限"
										, "render": function (
											data, type,
											row, meta) {
											let rolesname = "";
											data.forEach(role => {
												rolesname += role.name + "<br>"
											});
											return rolesname
										}
										, responsivePriority: 20
									},
									{
										"data": 'birthday'
										, "title": "生日"
										, "render": function (
											data, type,
											row, meta) {
											if (data != null) {
												moment(Date.parse(data)).format('YYYY-MM-DD')
												data = moment(Date.parse(data)).format('YYYY/MM/DD')
												return data;
											}
											return " ";
										}
									},
									{
										"data": 'address'
										, "title": "地區"
									},
									{
										"data": 'gender'
										, "title": "性別"
									},
									{
										"data": 'level'
										, "title": "等級"
									},
									{
										"data": 'exp'
										, "title": "經驗值"
									},

									{
										"data": 'point'
										, "title": "點數"
									},
									{
										"data": "registerdata"
										, "title": "註冊日期"
										, responsivePriority: 50

										, "render": function (
											data, type,
											row, meta) {
											moment(Date.parse(data)).format('YYYY-MM-DD')
											data = moment(Date.parse(data)).format('YYYY/MM/DD')
											return data
										}
									},
									{
										"data": 'subscribed'
										, "title": "訂閱狀態"
										, "render": function (
											data, type,
											row, meta) {
											if (data) {
												return "已訂閱"
											} else {
												return "未訂閱"
											}
										}
									},
									{
										"data": 'about'
										, "title": "關於我"
									},
									{

										"title": "修改",
										render: function (data, type, row, meta) {
											return '<button class=\"datatable_edit_button\" onclick=\"edituser(\'' + meta.row + '\')\"><i class=\"fas fa-sliders-h\"></i></button>'
										}
										, responsivePriority: 11
									},
									{
										"title": "刪除",
										render: function (data, type, row, meta) {
											return '<button class=\"datatable_del_button\"   onclick=\"deluser(\'' + meta.row + '\')\"><i class=\"fas fa-trash-alt\"></i></button>'
										}
										, responsivePriority: 12
									}
								],
								responsive: true,
								columnDefs: [
									{
										"defaultContent": ""
										, "targets": "_all"
									},
								],
								breakpoints: [
									{ name: 'desktop', width: Infinity },
									{ name: 'tablet-l', width: 1024 },
									{ name: 'tablet-p', width: 767 },//原本是768~1024不含768
									{ name: 'mobile-l', width: 480 },
									{ name: 'mobile-p', width: 320 }
								],
							})
					// 翻頁響應刷新
					$('.dataTables_paginate').on('click', function () {
						table.responsive.recalc();
						setTimeout(function () {
							table.responsive.recalc();
						}, 500);
					});
					// 頁面顯示響應刷新
					$('#memberlist_length select').on('change', function () {
						table.responsive.recalc();
						setTimeout(function () {
							table.responsive.recalc();
						}, 500);
					});
					// 選取整ROW
					$('#memberlist tbody').on('click', 'tr', function () {
						let index = table.row(this).index();
					});
					// 表頭不換行
					$('#memberlist thead tr th').css('white-space', 'nowrap');
					table.responsive.recalc();
					// 響應刷新
					setTimeout(function () {
						table.responsive.recalc();
					}, 500);
				});
	});

// 修改選單
function edituser(index) {
	editin = index;
	fetch("/morari/camper/html/useredit.html")
		.then(response => response.text())
		.then(html => {
			// USER編輯html
			document.querySelector(".useredit").innerHTML = html;
			// 生成roles選項
			const rolecheckbox = document.getElementById('rolecheckbox');
			fetch('/morari/api/auth/roles')
				.then(response => response.json())
				.then(roles => {
					roles.forEach(role => {
						const label = document.createElement('label');
						const checkbox = document.createElement('input');
						checkbox.type = 'checkbox';
						checkbox.name = 'roletype';
						checkbox.id = role.rid;
						checkbox.value = role.name;
						checkbox.checked = 'checked';
						const span = document.createElement('span');
						span.className = 'form-control form-control-edit round button';
						span.innerText = role.name;
						label.appendChild(checkbox);
						label.appendChild(span);
						rolecheckbox.appendChild(label);
					});

					// 拿取欄位資訊帶入編輯欄位
					let userdata = receivedData[index];
					document.getElementById("titleuid").innerHTML = '<img src="https://storage.googleapis.com/morari/adminshot" alt="shot" style="max-width: 80px; padding: 0px; border: 1px ; border-radius: 10px;" id="shotimg" class="btn"> <input type="file" class="form-control-file" id="shotInput" accept="image/*" style="display: none;">' + userdata.uid;
					for (const key in userdata) {
						if (userdata.hasOwnProperty(key)) {
							const element = userdata[key];
							if (key == "shot") {
								document.getElementById("shotimg").src = element;
								document.getElementById("shot").value = element;
							} else if (key == "roles") {
								element.forEach(r => {
									const roles = r.name;
									let checkboxes = document.getElementById("rolecheckbox").getElementsByTagName("input");
									for (let index = 0; index < checkboxes.length; index++) {
										const element = checkboxes[index];
										if (roles == element.value) {
											element.checked = false;
										}

									}
								});
							} else if (key == "birthday") {
								let birthday = moment(Date.parse(element)).format('YYYY-MM-DD');
								// birthdayInput.value = today;
								document.getElementById("birthday").value = birthday;
							} else if (key == "uid") {
								edituid = element;
								document.getElementById(key).value = element;
								document.getElementById("titleuid").value = element;
							} else {
								document.getElementById(key).value = element;
							}
							let checkboxes = document.getElementById("rolecheckbox").getElementsByTagName("input");
							for (let index = 0; index < checkboxes.length; index++) {
								const element = checkboxes[index];
								element.checked = !element.checked;
							}
						}
					}
					// dialog顯示
					document.querySelector(".useredit").showModal();
					// 點取圖片開啟上傳檔案
					document.getElementById("shotimg").addEventListener("click", function () {
						document.getElementById("shotInput").click();
					});
					// 關閉dialog按鈕
					const closeButton = document.getElementById("closeuseredit");
					closeButton.addEventListener("click", function () {
						document.querySelector(".useredit").close();
					});

					//   把圖片讀取後顯示
					document.getElementById("shotInput").addEventListener("change", function () {
						file = this.files[0];
						let reader = new FileReader();
						reader.onload = function (e) {
							document.getElementById("shotimg").src = e.target.result;
						};
						reader.readAsDataURL(file);
					});
				});
		})
}

function deluser(index) {


	if (confirm('確認刪除資料?') == true) {
		let userdata = receivedData[index];

		// console.log("del "+uid)

		// $.ajax({
		// 	url: "/campingmapping/DelMemberServlet",
		// 	type: "POST",
		// 	//提交方式
		// 	data: { "account": account },
		// 	dataType: "text",
		// }).done(function (data) {
		// 	if (data == 1) {
		// 		alert('刪除成功')
		// 		location.reload()
		// 	} else {
		// 		alert('刪除失敗')

		// 	}

		// })


	} else {
		// console.log('您已取消確認');
	}

};
// 儲存用戶資訊
function saveedit() {
	let formData = new FormData();
	formData.append("file", file);
	formData.append("uid", edituid);
	if (file) {
		fetch("/morari/guest/camper/api/shot", {
			method: "PUT",
			body: formData
		}).then(response => response.text())
			.then(a => {
				document.getElementById("shot").value = a;
				getuservalue();
			})
	} else {
		getuservalue();
	}
}
// 封裝用戶資訊並送出
function getuservalue() {
	let checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
	let rolesvalues = [];
	for (var i = 0; i < checkboxes.length; i++) {
		rolesvalues.push({
			rid: checkboxes[i].id,
			name: checkboxes[i].value
		});
	}
	let data = {
		uid: document.getElementById("uid").value,
		nickname: document.getElementById("nickname").value,
		firstname: document.getElementById("firstname").value,
		lastname: document.getElementById("lastname").value,
		email: document.getElementById("email").value,
		phone: document.getElementById("phone").value,
		roles: rolesvalues,
		birthday: document.getElementById("birthday").value,
		address: document.getElementById("address").value,
		gender: document.getElementById("gender").value,
		exp: document.getElementById("exp").value,
		level: document.getElementById("level").value,
		point: document.getElementById("point").value,
		registerdata: document.getElementById("registerdata").value,
		subscribed: document.getElementById("subscribed").value,
		shot: document.getElementById("shot").value,
		about: document.getElementById("about").value,
		accountnonlocked: document.getElementById("accountnonlocked").value,
		isenabled: document.getElementById("isenabled").value,
	};
	fetch("/morari/admin/camper/api/user", {
		method: "PUT",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(data)
	})
		.then(response => {
			if (response.status == 200) {
				alert('修改成功');
				window.location.reload();
			} else {
				alert('修改失敗');
				window.location.reload();
			}
		})
}
// 權限修改
function accountlocked(index) {
	let userdata = receivedData[index];
	let accountnonlocked = !userdata.accountnonlocked;
	fetch("/morari/admin/camper/api/accountlocked/" + userdata.uid, {
		method: "PUT",
		headers: {
			"Content-Type": "application/json"
		},
		body: accountnonlocked
	})
		.then(response => {
			if (response.status == 200) {
				alert('修改成功');
				window.location.reload();
			} else {
				alert('修改失敗');
				window.location.reload();
			}
		})
}
// 帳戶啟用
function enabled(index) {
	let userdata = receivedData[index];
	let isenabled = !userdata.isenabled;
	fetch("/morari/admin/camper/api/enabled/" + userdata.uid, {
		method: "PUT",
		headers: {
			"Content-Type": "application/json"
		},
		body: isenabled
	})
		.then(response => {
			if (response.status == 200) {
				alert('修改成功');
				window.location.reload();
			} else {
				alert('修改失敗');
				window.location.reload();
			}
		})

}

