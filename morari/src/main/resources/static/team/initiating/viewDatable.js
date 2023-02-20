fetch('view.controller', { method: 'GET' }).then(
			function (response) {
				if (response.status != 200) {
					return;
				}
				


				response.json().then(function (data) {
					
					optionData = '';
					optionData += "<option value=" + "0" + '"' + ">" + "請選擇表單號碼" + "</option>"
					
					data.forEach(function(row) {
    				if (row.pair === 1) {
      				row.pair = "不可配對";
    				} else if (row.pair === 0) {
      				row.pair = "可配對";
    				}
    				optionData += "<option value=" + row.initiatingnum + '"' + ">" + row.initiatingnum + "</option>"
  				});
  					//<option>放入表單號碼
    				document.getElementById('initiatingnum').innerHTML = optionData;
    				
    				//露營地點不重複，且放入<option>
    				optionData = '';
					var campareaData = data.filter(function(item, index, self) {
					    return index === self.findIndex(function(t) {
					        return t.camparea === item.camparea;
					    });
					});
					optionData += "<option value=" + "0" + ">" + "請選擇露營地點" + "</option>"
					for (var i = 0; i < campareaData.length; i++){
						optionData += "<option value=" + campareaData[i].camparea + ">" + campareaData[i].camparea + "</option>"
					}
					document.getElementById('camparea').innerHTML = optionData;
					
					//發文會員不重複
					optionData = '';
					var uidData = data.filter(function(item, index, self) {
					    return index === self.findIndex(function(t) {
					        return t.userprofiles === item.userprofiles;
					    });
					});
					optionData += "<option value=" + "0" + ">" + "請選擇發文會員" + "</option>"
					for (var i = 0; i < uidData.length; i++){
						optionData += "<option value=" + '"' + uidData[i].userprofiles.uid + '"' + ">" + uidData[i].userprofiles.uid + "</option>"
					}
					document.getElementById('userprofiles').innerHTML = optionData;
					
					$('#result').DataTable({
      				data: data,
      				responsive: true,
      				columns: [
        			
      				{ data: 'initiatingnum', title: '揪團編號' },
      				{ data: 'userprofiles.uid', title: '發文會員' },
      				{ data: 'postdate', title: '發文日期', render: function (data, type, row) {
      					return data.split('T')[0];
    					} },
      				{ data: 'startdate', title: '開始日期', render: function (data, type, row) {
      					return data.split('T')[0];
    					} },
      				{ data: 'enddate', title: '結束日期', render: function (data, type, row) {
      					return data.split('T')[0];
    					} },
      				{ data: 'currentnum', title: '目前人數' },
      				{ data: 'acceptablenum', title: '接受人數' },
      				{ data: 'camparea', title: '露營地點' },
      				{ data: 'pair', title: '配對狀態' },
					{
						data: null,
						title: "修改",
						width: "100px",
						render: function(data, type, row) {
									return `<form action="/morari/team/update.controller" method="get">
              			<input type="hidden" name="dnum" value="${data.initiatingnum}">
              			<input type="hidden" name="pman" value="${data.userprofiles.uid}">
              			<button style="border:none;background-color:transparent" type="submit" name="update"><a href="#" class="btn btn-warning btn-circle"><i class="fas fa-user-edit"></i></a></button>
            			</form>`;
								
								}
					},
					{
						data: null,
						title: "刪除",
						width: "100px",
						render: function(data, type, row) {
									return `<form action="/delete.controller/${data.initiatingnum}" method="delete">
              			<input type="hidden" name="dnum" value="${data.initiatingnum}">
              			<button style="border:none;background-color:transparent" type="button" name="delete"><a href="#" class="btn btn-danger btn-circle"><i class="fas fa-trash-alt"></i></a></button>
            			</form>`;
								
								}
					},
      							],
      				lengthMenu: [5, 10, 15, 20],
      				language: {
					"lengthMenu":     "顯示 _MENU_ 筆資料",
					"info":           "顯示第 _START_ 至 _END_ 筆資料，共 _TOTAL_ 筆",
					"search":         "搜尋：",
					"paginate": {
         				"next":       "下一頁",
         				"previous":   "上一頁"
      				}
					}
      							
    							});
							});
			});
		
		var xh = new XMLHttpRequest();
		xh.open('get', 'view.controller', true);
		xh.send();
		
		document.getElementById("result").addEventListener("click", function(event) {
			  if (event.target.name === "delete") {
				  	var name = event.target.name;
		            var formData = new FormData(event.target.form);
		            var value = formData.get('dnum');
		            
		            // 發出 DELETE 請求
		            var xhr = new XMLHttpRequest();
		            if(name == "delete"){
		            	var result = confirm("確定要刪除此筆資料嗎？");
		            	
		            	if(result){
		           		xhr.open('DELETE', 'delete.controller/{' + value + "}");
		            	xhr.onload = function() {
		            	  if (xhr.status === 200) {
		            	    location.href="/morari/team/teammanager.controller";
		            	  }
		            	  else {
		            	    console.error(xhr.responseText);
		            	  }
		            	};
		           		xhr.send();
		            	}
		            
		            }
			  }
		});
	
		xh.onload = function () {

		var data = JSON.parse(xh.responseText);
		
		var num = 0;
		
			document.querySelectorAll("button").forEach(function(button) {
		        button.addEventListener("click", function(event) {
		        	
		        	// 取得表單輸入
		        	var name = button.name;
		            var formData = new FormData(button.form);
		            var value = formData.get('dnum'); 
		           
		     	    if(name == "select"){
		     			
		     			if (num > 0) {
    						// datatable 存在，销毁它
    						$("#selectResult").DataTable().destroy();
						}
		     			
		     			var startdate = formData.get('startdate');
		     			var enddate = formData.get('enddate');
		     			
		     			if(startdate > enddate && startdate != "" && enddate != ""){
		     				alert("起始日期不可小於結束日期，這不是時空旅行！！！");
		     				return;
		     			}else{
		     				
		     			var inum = formData.get('initiatingnum');
			     		formData.delete("initiatingnum");
			     		formData.append("initiatingnum", parseInt(inum));
			     		var uid = formData.get("userprofiles");
			     		formData.delete("userprofiles");
		     				
		     			const data = Object.fromEntries(formData);
						const json = JSON.stringify(data);
		     			
		     			fetch('select.controller/{' + uid + '}', {
							method: 'POST',
							headers: {
								'Content-Type': 'application/json'
							},
							body: json
						})
							.then(function (response) {
								if (response.status == 200) {
									response.json().then(function (data){
								num += 1;
										data.forEach(function(row) {
    				if (row.pair === 1) {
      				row.pair = "不可配對";
    				} else if (row.pair === 0) {
      				row.pair = "可配對";
    				}
  					});
					
					$('#selectResult').DataTable({
      				data: data,
      				responsive: true,
      				searching: false,
      				
      				columns: [
      				{ data: 'initiatingnum', title: '揪團編號' },
      				{ data: 'userprofiles.uid', title: '發文會員' },
      				{ data: 'postdate', title: '發文日期', render: function (data, type, row) {
      					return data.split('T')[0];
    					} },
      				{ data: 'startdate', title: '開始日期', render: function (data, type, row) {
      					return data.split('T')[0];
    					} },
      				{ data: 'enddate', title: '結束日期', render: function (data, type, row) {
      					return data.split('T')[0];
    					} },
      				{ data: 'currentnum', title: '目前人數' },
      				{ data: 'acceptablenum', title: '接受人數' },
      				{ data: 'camparea', title: '露營地點' },
      				{ data: 'pair', title: '配對狀態' },

      							],
      				
      				language: {
					"lengthMenu":     "每頁顯示 10 筆資料",
					"info":           "顯示第 _START_ 至 _END_ 筆資料，共 _TOTAL_ 筆",
					"search":         "搜尋：",
					"paginate": {
         				"next":       "下一頁",
         				"previous":   "上一頁"
      				}
					}
      							
    							});
									
									
										
									});
								}
							});
		     			
		     			}//else結束
		     			
		            }
		            
		        });
		    });
		}