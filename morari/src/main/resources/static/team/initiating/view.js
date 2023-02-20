fetch('view.controller', { method: 'GET' }).then(
			function (response) {
				if (response.status != 200) {
					return;
				}
				

				response.json().then(function (data) {
					var optionData= "";
					
					var tableData = "";
					tableData += "<th>"  +"<td>" + "揪團編號" + 
					"</td>" + "<td>" + "發文會員" + 
					"</td>" + "<td>" + "發文日期" + "</td>" + "<td>" + "開始日期" + "</td>" + "<td>" +
					"結束日期" + "</td>" + "<td>" + "目前人數" + "</td>" + "<td>" + "接受人數" + "</td>" +
					"<td>" + "露營地點" + "</td>" + "<td>" + "配對狀態" + "</td>" + "</th>";
					optionData += "<option value=" + "0" + '"' + ">" + "請選擇表單號碼" + "</option>"
					for (var i = 0; i < data.length; i++) {
						var pDay = new Date(data[i].postdate).toLocaleDateString("zh-TW", {year: 'numeric', month: '2-digit', day: '2-digit'});
						var sDay = new Date(data[i].startdate).toLocaleDateString("zh-TW", {year: 'numeric', month: '2-digit', day: '2-digit'});;
						var eDay = new Date(data[i].enddate).toLocaleDateString("zh-TW", {year: 'numeric', month: '2-digit', day: '2-digit'});;
						
						var pair = "可配對";
						if (data[i].pair != 0) {
							pair = "不可配對"
						}
						
							
						tableData += "<tr>" + "<td><form " + 
						"action=" + "/delete.controller/" + "{" + data[i].initiatingnum + "}" + 
						" method=" + '"delete"' +
						"><input type="+ '"hidden"' + "name=" + '"dnum"' + "value=" + '"' + data[i].initiatingnum + '"' +">" + 
						"<button type=" + '"button"' + "name=" + '"delete"' + ">" + "刪除" + "</button>" + "</form>"+
						
						"<form " + "action=" + "/morari/team/update.controller" + 
						" method=" + '"get"' +
						"><input type="+ '"hidden"' + "name=" + '"dnum"' + "value=" + '"' + data[i].initiatingnum + '"' +">" +
						"<input type="+ '"hidden"' + "name=" + '"pman"' + "value=" + '"' + 
						data[i].userprofiles.uid + 
						'"' +">"+
						"<button type=" + '"submit"' + "name=" + '"update"' + ">" + "修改" + "</button>" + "</form>"
						
						
						+ "</td>" + "<td>" + data[i].initiatingnum + 
						"</td>" + "<td>" + 
						data[i].userprofiles.uid + 
						"</td>" + "<td>" + pDay + "</td>" + "<td>" + sDay + "</td>" + "<td>" +
						eDay + "</td>" + "<td>" + data[i].currentnum + "</td>" + "<td>" + data[i].acceptablenum + "</td>" +
						"<td>" + data[i].camparea + "</td>" + "<td>" + pair + "</td>" +"</tr>" + 
						"<tr><td><form><button type=button>顯示留言</button></form></td></tr>"
						
						optionData += "<option value=" + data[i].initiatingnum + '"' + ">" + data[i].initiatingnum + "</option>"
					}
					document.getElementById('result').innerHTML = tableData;
					document.getElementById('initiatingnum').innerHTML = optionData;
					
					//露營地點不重複
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
			
			document.querySelectorAll("button").forEach(function(button) {
		        button.addEventListener("click", function(event) {
		        	
		        	// 取得表單輸入
		        	var name = button.name;
		            var formData = new FormData(button.form);
		            var value = formData.get('dnum'); 
		           
		     	    if(name == "select"){
		     			
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
									
									});
								}
							});
		     			
		     			}//else結束
		     			
		            }
		            
		        });
		    });
		}