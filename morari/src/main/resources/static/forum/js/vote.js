var option = 0;
var votingdiv = "";
var voteddiv = "";

// 載入 你的.html
fetch("/morari/forum/html/vote.html")
    .then(response => response.text())
    .then(html => {
        // 將載入的 HTML 放入 .footer 元素中
        document.querySelector(".vote").innerHTML = html;

	// 顯示畫面
	$(function () {
		// 進行中的投票
		$.ajax({
			url: "/morari/showvoting.controller",
			dataType: "JSON",
			contentType: "application/json",
			success: function (data) {
				if (data.length !== 0) {
					// i=index 第幾個 n=element 元素
					$.each(data, function (i, n) {
						if(i%2==0){
							votingdiv += "<div class='col-md-12 room'><div class='row'>";
						}
						votingdiv += "<div class='col-md-5' style='border-radius:10px; background-color:white; margin:0px 47px'><div class='room-des'>"
						votingdiv += "<h3>" + n.votename + "</h3><ul class='room-size' id='option"+ i +"'>";
							
						// 選項
						$.ajax({
							url: "/morari/showvoteoptionbyvoteid.controller/" + n.voteid,
							dataType: "JSON",
							contentType: "application/json",
							success: function (data) {
//								var div = "<div style='border:1px solid white; border-radius:10px; width:50% ;background-color:white; padding:10px'><h3>" + n.votename + "</h3>";
								$.each(data, function (j, m) {
									var newoption = "<li><input type='radio' name='voteid" + n.voteid + "' id='radiooption" + (j + 1) + "' onclick=radioclick(" + m.optionid + ")>" + m.optionname + "</li>";
//									div += "<input type=\"radio\" name=\"voteid" + n.voteid + "\" id=\"radiooption" + (j + 1) + "\" onclick=radioclick(" + m.optionid + ")>" + m.optionname + " ";
									$("#option" + i).append(newoption);
								});
								var sendvote = "<br><button onclick=sendvote() style='margin:5px'>送出</button>";
								$("#option" + i).append(sendvote);
							}
						});
						
						votingdiv += "</div></div>"	
						if(i%2==1){
							votingdiv += "</div></div>";
						}
					});
				} else {
					votingdiv += "<div class='col-md-12 room'><div class='row'><div class='room-des'>沒有進行中的投票</div></div></div>";
				}
				$("#voting").append(votingdiv);
			}
		});
	
		// 已結束的投票
		$.ajax({
			url: "/morari/showvoted.controller",
			dataType: "JSON",
			contentType: "application/json",
			success: function (data) {
				if (data.length !== 0) {
					// i=index 第幾個 n=element 元素
					$.each(data, function (i, n) {
						if(i%2==0){
							voteddiv += "<div class='col-md-12 room'><div class='row'>";
						}
						voteddiv += "<div class='col-md-5' style='border-radius:10px; background-color:white; margin:0px 47px'><div class='room-des'>"
						voteddiv += "<h3>" + n.votename + "</h3><ul class='room-size' id='endoption"+ i +"'>";
						
						// 選項
						$.ajax({
							url: "/morari/showvoteoptionandvoterecordbyvoteid.controller/" + n.voteid,
							dataType: "JSON",
							contentType: "application/json",
							success: function (data) {
//								var div = "<div style='border:1px solid white; border-radius:10px; width:50% ;background-color:white; padding:10px'><h3>" + n.votename + "</h3>";
								$.each(data, function (j, m) {
									var endoption = m.optionname + " : " + m.recordcount + "筆投票<br>";
									$("#endoption" + i).append(endoption);
								});
								
								let winnerdiv = "";
								// 開獎
								if(n.winner){
									let email = n.winner.email;
									let arr = email.split('@');
									let first = arr[0].slice(0,3) + "***";
									let last = arr[1];
									let winner = first + "@" + last;
									winnerdiv += "<h3 style='color:red; margin:5px;'>中獎者：" + winner + "</h3>";
								}else{
									winnerdiv += "<h3 style='color:orange; margin:5px;'>尚未開獎</h3>";
								}
								$("#endoption" + i).append(winnerdiv);
							}
						});
						
						voteddiv += "</div></div>"	
						if(i%2==1){
							voteddiv += "</div></div>";
						}
					});
				} else {
					voteddiv += "<div class='col-md-12 room'><div class='row'><div class='room-des'>沒有已完成的投票</div></div></div>";
				}
				$("#voted").append(voteddiv);
			}
		});
	
	});
});

function sendvote() {
	$.ajax({
		type: "post",
		url: "/morari/insertvoterecord.controller/" + option,
		dataType: "JSON",
		contentType: "application/json",
		success: function (data) {
			if (data == true) {
				alert("投票成功");
				location.reload();
			} else {
				alert("您已投票過，不可重複投票");
			}
		}
	});
}

function radioclick(optionid) {
	option = optionid;
}

