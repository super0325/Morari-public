// 載入 你的.html
//fetch("/morari/work/html/jobCrud.html")
//	.then(response => response.text())
//	.then(html => {
//		// 將載入的 HTML 放入 .footer 元素中
//		document.querySelector(".crud").innerHTML = html;
//	});
   function selectUid(uid) {
		$.ajax({
			type:'POST',
			url: '/morari/admin/resume/selectUid.controller/'+uid,
			dataType:'json',
			success: function (data) {
				$('#showInsert').empty("");
				$('#su').empty("");
				if (data.length === 0) {
					var h3 = $('#su');
					h3.prepend('查詢結果');
					var table = $('#showInsert');
					table.append("<tr><td colspan='2'>暫無資料</td></tr>");
				} else {
					var h3 = $('#su');
					h3.prepend('查詢結果');
					var table = $('#showInsert');
					table.append("<tr><th>刊登編號</th><th>履歷編號</th><th>會員編號</th><th>應徵職缺</th><th>姓名</th><th>年次</th><th>性別</th><th>email</th><th>電話</th><th>學歷</th><th>經歷</th><th>填寫時間</th><th></th></tr>");

					$.each(data, function (i, n) {
						var tr = "<tr align='center'>" + 
						"<td>" + n.job.rackid + "</td>" +
    					"<td>" + n.number + "</td>" +
						"<td>" + n.userprofiles.uid +"</td>"+
						"<td>" + n.work + "</td>" + 
						"<td>" + n.name + "</td>" + 
						"<td>" + n.age + "</td>" +
						"<td>" + n.gender + "</td>" +
						"<td>" + n.mail + "</td>" + 
						"<td>" + n.phone + "</td>" + 
						"<td>" + n.educational + "</td>" + 
						"<td>" + n.experience + "</td>" +
						"<td>" + n.ptime + "</td>" +
    					"<td><form action='update.controller/" + n.number + "' method='POST'><input type='submit' value='修改'></form></td>" +
    					"<td><button id='delete' onclick='resumeDelete(" + n.number + ")'>刪除</button></td>" + "</tr>";
						table.append(tr);
					});
				}
			}
		});
	};
	function selectRackid(rackid) {
			$.ajax({
				type:'POST',
				url: '/morari/admin/resume/selectRid.controller/'+rackid,
				dataType:'json',
				success: function (data) {
					$('#showInsert').empty("");
					$('#su').empty("");
					
					if (data.length == 0||data==null) {
						$('table').prepend("<tr><td colspan='2'>暫無資料</td></tr>");
					} else {
						var h3 = $('#su'); 
						h3.prepend('查詢結果');
						var table = $('#showInsert');
    				
    				table.append("<tr><th>刊登編號</th><th>履歷編號</th><th>會員編號</th><th>應徵職缺</th><th>姓名</th><th>年次</th><th>性別</th><th>email</th><th>電話</th><th>學歷</th><th>經歷</th><th>填寫時間</th><th></th></tr>");


    				$.each(data, function(i, n) {
    					var tr = "<tr align='center'>" + 
    					"<td>" + n.job.rackid + "</td>" +
    					"<td>" + n.number + "</td>" +
						"<td>" + n.userprofiles.uid +"</td>"+
						"<td>" + n.work + "</td>" + 
						"<td>" + n.name + "</td>" + 
						"<td>" + n.age + "</td>" +
						"<td>" + n.gender + "</td>" +
						"<td>" + n.mail + "</td>" + 
						"<td>" + n.phone + "</td>" + 
						"<td>" + n.educational + "</td>" + 
						"<td>" + n.experience + "</td>" +
						"<td>" + n.ptime + "</td>" +
    					"<td><form action='update.controller/" + n.number + "' method='POST'><input type='submit' value='修改'></form></td>" +
    					"<td><button id='delete' onclick='resumeDelete(" + n.number + ")'>刪除</button></td>" + "</tr>";
    					table.append(tr);
						});
					}
				}
			});
		};
	
	function resumeDelete(number){
			if(confirm("確定刪除該筆資料(履歷編號:"+ number + ")?")){
				$.ajax({
					type: 'delete',
					url: '/morari/admin/resume/resumeDelete.controller/'+number,
					dataType: 'TEXT',
					success: function(data){
						alert(data);
						location.reload();
					}
				});
			}else{				
			}
		};