let postid;

// 載入 你的.html
fetch("/morari/forum/html/updatepost.html")
	.then(response => response.text())
	.then(html => {
		// 將載入的 HTML 放入 .footer 元素中
		document.querySelector(".updatepost").innerHTML = html;
	}).then(()=>{

		$(document).ready(function () {
			// postid
			let url = window.location.href;
			let arr = url.split('/');
			postid = arr[arr.length - 1];
			
			var file;
			var originalPicture;
		
			// 修改完成
			$("#sub").click(function () {
				if (confirm("是否確定修改?")) {
					if ($.trim($("#title").val()) == "") {
						alert("請輸入標題");
						return;
					}
					if ($.trim($("#postcontent").val()) == "") {
						alert("請輸入內容");
						return;
					}
					if($("#price").val() < 0 || $("#price").val() > 2147483647){
						alert("輸入金額有誤");
						return;
					}
					if($("#endDate").val() == "" && $("#startDate").val() !== "" || $("#endDate").val() !== "" && $("#startDate").val() == ""){
						alert("需同時輸入開始露營日期及結束露營日期");
						return;
					}
					if ($("#endDate").val() < $("#startDate").val()) {
						alert("結束露營日期早於開始露營日期");
						return;
					}
					
					// 照片傳到google
					let formData = new FormData();
					if(file){
						formData.append("file" ,file);
						$.ajax({
							type:"post",
							url:"/morari/insertpicture.controller",
							contentType: false,
							processData: false,
							data:formData,
							success: function(data){
								update(data);
							}
						});
					}else{
						update(originalPicture);
					}
		
//					var params = {
//						"postid": postid,
//						"title": $("#title").val(),
//						"content": $("#postcontent").val(),
//						"people": $("#people").val(),
//						"price": $("#price").val(),
//						"county": $("#county").val(),
//						"startdate": $("#startDate").val(),
//						"enddate": $("#endDate").val(),
//						"score": $("#score").val()
//					}
//		
//		
//					$.ajax({
//						type: "put",
//						url: "/morari/updatepost.controller",
//						dataType: "JSON",
//						contentType: "application/json",
//						data: JSON.stringify(params),
//						success: function (data) {
//							if (data == true) {
//								alert("更新成功")
//								window.location.href = "/morari/admin/forum/forumadminindex";
//							}
//						}
//					});
				}
		
			});
		
			// 顯示畫面
			$.ajax({
				url: "/morari/showpostbyid.controller/" + postid,
				dataType: "JSON",
				contentType: "application/json",
				success: function (data) {
					$("#title").val(data.title);
					$("#postcontent").val(data.content);
					$("#picture").attr("src", data.picture);
					originalPicture = data.picture;
		
					var peopleMax = 10;
					var option = document.createElement("option");
					option.value = "0";
					option.innerHTML = "請選擇";
					$("#people").append(option);
					for (let i = 1; i <= peopleMax; i++) {
						option = document.createElement("option");
						option.value = i;
						option.innerHTML = i;
						if (i == data.people) {
							option.selected = "selected";
						}
						$("#people").append(option);
					}
		
					if (data.price > 0) {
						$("#price").val(data.price);
					}
		
					var option = document.createElement("option");
					option.value = "";
					option.innerHTML = "請選擇";
					$("#county").append(option);
					var countyInner = ["台北市", "新北市", "基隆市", "桃園市", "新竹縣", "新竹市", "苗栗縣", "台中市", "彰化縣", "南投縣",
						"雲林縣", "嘉義縣", "嘉義市", "台南市", "高雄市", "屏東縣", "宜蘭縣", "花蓮縣", "台東縣", "澎湖縣", "金門縣", "連江縣"];
					var countyValue = ["TPE", "TPH", "KLU", "TYC", "HSH", "HSC", "MAL", "TXG", "CWH", "NTO", "YLH", "CHY",
						"CYI", "TNN", "KHH", "IUH", "ILN", "HWA", "TTT", "PEH", "KMN", "LNN"];
		
					for (let i = 0; i < countyValue.length; i++) {
						option = document.createElement("option");
						option.value = countyValue[i];
						option.innerHTML = countyInner[i];
						if (countyValue[i] == data.county) {
							option.selected = "selected";
						}
						$("#county").append(option);
					}
		
					if (data.startdate != "" && data.startdate != null) {
						var date = moment(Date.parse(data.startdate)).format('YYYY-MM-DD');
						$("#startDate").val(date);
					}
		
					if (data.enddate != "" && data.enddate != null) {
						var date = moment(Date.parse(data.enddate)).format('YYYY-MM-DD');
						$("#endDate").val(date);
					}
		
					var scoreMax = 5;
					var option = document.createElement("option");
					option.value = "0";
					option.innerHTML = "請選擇";
					$("#score").append(option);
					for (let i = 1; i <= scoreMax; i++) {
						option = document.createElement("option");
						option.value = i;
						option.innerHTML = i;
						if (i == data.score) {
							option.selected = "selected";
						}
						$("#score").append(option);
					}
				}
			});
			
			// 更新照片
			$("#newpicture").change(function(){
				file = this.files[0];
				if(file.size > 1000000){
					alert("照片大小限制1MB，請更換照片");
					file = "";
					$("#newpicture").val("");
					return;
			    }
				let reader = new FileReader();
				reader.onload = function (e) {
					$("#picture").attr("src", e.target.result);
				};
				reader.readAsDataURL(file);
			});
		
		});

	});
	
function update(picture){
	var params = {
		"postid": postid,
		"title": $("#title").val(),
		"content": $("#postcontent").val(),
		"picture":picture,
		"people": $("#people").val(),
		"price": $("#price").val(),
		"county": $("#county").val(),
		"startdate": $("#startDate").val(),
		"enddate": $("#endDate").val(),
		"score": $("#score").val()
	}

	$.ajax({
		type: "put",
		url: "/morari/updatepost.controller",
		dataType: "JSON",
		contentType: "application/json",
		data: JSON.stringify(params),
		success: function (data) {
			if (data == true) {
				alert("更新成功")
				window.location.href = "/morari/admin/forum/forumadminindex";
			}
		}
	});
}



