// 載入 你的.html
function selectJob(job) {
		$.ajax({
			type:'POST',
			url: '/morari/admin/work/selectLike.controller/'+job,
			dataType:'json',
			success: function (data) {
				$('#showInsert').empty("");
				$('#su').empty("");
				
				if (data.length == 0||data== null) {
					$('table').prepend("<tr><td colspan='2'>暫無資料</td></tr>");
				} else {
					var h3 = $('#su');
					h3.prepend('查詢結果');
					var table = $('#showInsert');
					table.append("<tr><th>會員編號</th><th>刊登編號</th><th>刊登時間</th><th>職缺</th><th>薪資</th><th>人數</th><th>地點</th><th>可上班日期</th><th>可上班時段</th><th>備註</th><th>照片</th><th></th></tr>");

					$.each(data, function (i, n) {
						var tr = "<tr align='center'>" + 
						"<td>" + n.userprofiles.uid + "</td>" + 
						"<td>" + n.rackid + "</td>" +
						"<td>" + n.rackup + "</td>" + 
						"<td>" + n.job + "</td>" + 
						"<td>" + n.salary + "</td>" + 
						"<td>" + n.quantity + "</td>" + 
						"<td>" + n.place + "</td>" + 
						"<td>" + n.date + "</td>" + 
						"<td>" + n.time + "</td>" + 
						"<td>" + n.remark + "</td>" + 
						"<td>" + n.img + "</td>" + 
						"<td><form action='update.controller/"+ n.rackid +"' method='POST'><input type='submit' value='修改'></form></td>"+ 
						"<td><button id='delete' onclick='jobDelete("+ n.rackid +")'>刪除</button>" + "</td>" +"</tr>";
						table.append(tr);
					});
				}
			}
		});
	};
	function selectUid(uid) {
		$.ajax({
			type:'POST',
			url: '/morari/admin/work/selectUid.controller/'+uid,
			dataType:'json',
			success: function (data) {
				$('#showInsert').empty("");
				$('#su').empty("");
				if (data.length == 0||data== null) {
					var h3 = $('#su');
					h3.prepend('查詢結果');
					var table = $('#showInsert');
					table.append("<tr><td colspan='2'>暫無資料</td></tr>");
				} else {
					var h3 = $('#su'); 
					h3.prepend('查詢結果');
					var table = $('#showInsert');
					table.append("<tr><th>會員編號</th><th>刊登編號</th><th>刊登時間</th><th>職缺</th><th>薪資</th><th>人數</th><th>地點</th><th>可上班日期</th><th>可上班時段</th><th>備註</th><th>照片</th><th></th></tr>");

					$.each(data, function (i, n) {
						var tr = "<tr align='center'>" + 
						"<td>" + n.userprofiles.uid + "</td>" + 
						"<td>" + n.rackid + "</td>" +
						"<td>" + n.rackup + "</td>" + 
						"<td>" + n.job + "</td>" + 
						"<td>" + n.salary + "</td>" + 
						"<td>" + n.quantity + "</td>" + 
						"<td>" + n.place + "</td>" + 
						"<td>" + n.date + "</td>" + 
						"<td>" + n.time + "</td>" + 
						"<td>" + n.remark + "</td>" + 
						"<td>" + n.img + "</td>" + 
						"<td><form action='update.controller/"+ n.rackid +"' method='POST'><input type='submit' value='修改'></form></td>"+ 
						"<td><button id='delete' onclick='jobDelete("+ n.rackid +")'>刪除</button>" + "</td>" +"</tr>";
						table.append(tr);
					});
				}
			}
		});
	};

	function jobDelete(rackid){
			if(confirm("確定刪除該筆資料(刊登編號:"+ rackid + ")?")){
				$.ajax({
					type: 'delete',
					url: '/morari/admin/work/jobDelete.controller/'+rackid,
					dataType: 'TEXT',
					success: function(data){
						alert(data);
						location.reload();
					}
				});
			}else{				
			}
		};