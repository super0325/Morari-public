// 載入 你的.html
fetch("/morari/forum/html/newvote.html")
    .then(response => response.text())
    .then(html => {
        // 將載入的 HTML 放入 .footer 元素中
        document.querySelector(".newvote").innerHTML = html;

		$(document).ready(function () { 		
			var optionnumber = 3;
 			//一鍵匯入
 			$("#fastinput").click(function(){
				fastinput();
 			});
 			
 			// 新增畫面選項
	 		$("#newoption").click(function(e){
				e.preventDefault();
				var newvoteoption = "<label style='margin:0px 0px 0px 7px;'>選項" + optionnumber + "</label><br><input type=\"text\" id=\"option" + optionnumber + "\" maxlength='50' size='80' style='margin: 0px 0px 5px 0px'><br>";
				$("#newvoteoption").append(newvoteoption);
				optionnumber += 1;
			});
 		
	 		// 新增投票
	 		$("#sendvote").click(function(){		
				if($.trim($("#title").val()) == ""){
					alert("請輸入投票主題");
					return;
				}
				if($.trim($("#option1").val()) == ""){
					alert("請輸入選項1");
					return;
				}
				if($.trim($("#option2").val()) == ""){
					alert("請輸入選項2");
					return;
				}
				
				var newvote = {
					"votename":$("#title").val(),
					"voting":1
					}
								
				$.ajax({
					type:"post",
					url:"/morari/insertvote.controller",
					dataType:"JSON",
					contentType:"application/json",
					data:JSON.stringify(newvote),
					success: function(data){
						for(let i=1; i<optionnumber; i++){
							
							if($.trim($("#option" + i).val()) !== ""){
							
								var newvoteoption = {
									"optionname":$("#option" + i).val(),
									"vote":data
								}
								
								$.ajax({
									type:"post",
									url:"/morari/insertvoteoption.controller",
									dataType:"JSON",
									contentType:"application/json",
									data:JSON.stringify(newvoteoption),
									success: function(){
									}
								});
							}
						}
						alert("新增投票成功");
						window.location.href = "/morari/admin/forum/showvoteadmin.controller";
					}
				});
			});
		});
    });

function fastinput(){
	$("#title").val("CP值最高的營區？");
	$("#option1").val("小蜜蜂");
	$("#option2").val("森林遊趣");
	$("#option3").val("蔚藍海岸");
	$("#option4").val("聽風");
}
