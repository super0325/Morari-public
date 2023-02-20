// 載入 你的.html
fetch('/morari/mall/selectAllPd', { method: 'GET' }).then(
			function (response) {
				if (response.status != 200) {
					return;
				}
				response.json().then(function (n) {
					var tableData = 
						"<th>產品編號</th>"+"<th>產品名稱</th>"+
						"<th>會員id</th>"+"<th>品牌名稱</th>"+
						"<th>產品規格</th>"+	"<th>類型</th>"+
						"<th>照片</th>"+"<th>產品價格</th>"+
						"<th>產品庫存</th>"+"<th>上架時間</th>"+
						"<th>產品修改時間</th>"+"<th>修改</th>"+
						"<th>刪除</th>";
					for (var i = 0; i < n.length; i++) {
						tableData += 
							"<tr>" +
				            "<td>" + n[i].pdid + "</td>" + "<td>" + n[i].pdname + "</td>" +
				            "<td>" + n[i].userid + "</td>" +"<td>" + n[i].pdtitle + "</td>"  +
				            "<td>" + n[i].pdcontent + "</td>" +"<td>" + n[i].pdtype + "</td>"  +
				            "<td>" + n[i].pdpicture + "</td>" + "<td>" + n[i].pdprice + "</td>" +
				            "<td>" + n[i].pdinventory + "</td>" +"<td>" + n[i].pddate + "</td>" +
				            "<td>" + n[i].pdlastupdate + "</td>" +
				            
				            "<td><button id='update' type='button' value='n[i].pdid' onclick='updateProduct(" + n[i].pdid + ")'>修改</button></td>" +
				            "<td><button id='delete' type='button' value='n[i].pdid' onclick='confirmDelete(" + n[i].pdid + ")'>刪除</button></td>" +
							
				            "</tr>"
						
					}
					document.getElementById('result').innerHTML = tableData;
				});
				
			});
		
		function confirmDelete(pdid) {
			if (confirm("確定刪除此項產品資料(編號:" + pdid + ")?")) {
				$.ajax({
					   url:'/morari/mall/deleteByPdid/'+pdid,
					   type:'delete',
					   dataType:'text',
					   success: function(data){
					   alert(data);
					   location.reload();
					   },
						error: function(){
						// 刪除失敗時的處理
						alert('刪除失敗');
					}
				
				  });
			} else {
			}
		}
		function updateProduct(pdid) {
			if (confirm("確定修改此項產品資料(編號:" + pdid + ")?")) {
				$.ajax({
					   success: function(data){
					   window.location.href = "/morari/mall/updateproduct.controller/"+pdid;
					   },
						error: function(){
						// 刪除失敗時的處理
						alert('刪除失敗');
					}
				
				  });
				
			} else {
			}
		}