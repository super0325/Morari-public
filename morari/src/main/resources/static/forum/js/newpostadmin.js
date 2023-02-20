// 載入 你的.html
fetch("/morari/forum/html/newpost.html")
    .then(response => response.text())
    .then(html => {
        // 將載入的 HTML 放入 .footer 元素中
        document.querySelector(".newpost").innerHTML = html;

		$(document).ready(function () {
			var file;

			// 一鍵輸入 
			$("#fastinput").click(function () {
				fastinport();
			});

			// 新增貼文
			$("#sendnewpost").click(function () {
				if ($.trim($("#title").val()) == "") {
					alert("請輸入標題");
					return;
				}
				if ($.trim($("#postcontent").val()) == "") {
					alert("請輸入內容");
					return;
				}
				if ($("#price").val() < 0 || $("#price").val() > 2147483647) {
					alert("輸入金額有誤");
					return;
				}
				if ($("#endDate").val() == "" && $("#startDate").val() !== "" || $("#endDate").val() !== "" && $("#startDate").val() == "") {
					alert("需同時輸入開始露營日期及結束露營日期");
					return;
				}
				if ($("#endDate").val() < $("#startDate").val()) {
					alert("結束露營日期早於開始露營日期");
					return;
				}

				// 照片傳到google
				let formData = new FormData();
				if (file) {
					formData.append("file", file);
					$.ajax({
						type: "post",
						url: "/morari/insertpicture.controller",
						contentType: false,
						processData: false,
						data: formData,
						success: function (data) {
							insert(data);
						}
					});
				} else {
					insert();
				}
			});

			// 顯示畫面
			var peopleMax = 10;
			for (let i = 1; i <= peopleMax; i++) {
				var option = document.createElement("option");
				option.value = i;
				option.innerHTML = i;
				$("#people").append(option);
			}

			var countyInner = ["台北市", "新北市", "基隆市", "桃園市", "新竹縣", "新竹市", "苗栗縣", "台中市", "彰化縣", "南投縣",
				"雲林縣", "嘉義縣", "嘉義市", "台南市", "高雄市", "屏東縣", "宜蘭縣", "花蓮縣", "台東縣", "澎湖縣", "金門縣", "連江縣"];
			var countyValue = ["TPE", "TPH", "KLU", "TYC", "HSH", "HSC", "MAL", "TXG", "CWH", "NTO", "YLH", "CHY", "CYI",
				"TNN", "KHH", "IUH", "ILN", "HWA", "TTT", "PEH", "KMN", "LNN"];
			for (let i = 0; i < countyInner.length; i++) {
				var option = document.createElement("option");
				option.value = countyValue[i];
				option.innerHTML = countyInner[i];
				$("#county").append(option);
			}

			var scoreMax = 5;
			for (let i = 1; i <= scoreMax; i++) {
				var option = document.createElement("option");
				option.value = i;
				option.innerHTML = i;
				$("#score").append(option);
			}

			// 顯示照片
			$("#picture").change(function () {
				file = this.files[0];
				if (file.size > 1000000) {
					alert("照片大小限制1MB，請更換照片");
					file = "";
					$("#picture").val("");
					return;
				}
				let reader = new FileReader();
				reader.onload = function (e) {
					$("#showpicture").attr("src", e.target.result);
				};
				reader.readAsDataURL(file);
			});
		});
	});

function insert(picture) {
	var params = {
		"title": $("#title").val(),
		"content": $("#postcontent").val(),
		"picture": picture,
		"people": $("#people").val(),
		"price": $("#price").val(),
		"county": $("#county").val(),
		"startdate": $("#startDate").val(),
		"enddate": $("#endDate").val(),
		"score": $("#score").val()
	}

	$.ajax({
		type: "post",
		url: "/morari/insertpost.controller",
		dataType: "JSON",
		contentType: "application/json",
		data: JSON.stringify(params),
		success: function (data) {
			if (data == true) {
				alert("新增成功")
				window.location.href = "/morari/admin/forum/forumadminindex";
			}
		}
	});
}

function fastinport() {
	$("#title").val("新竹露營區推薦蟬說：霧繞");
	$("#postcontent").val("位於新竹縣五峰鄉白蘭部落的「蟬說：霧繞」，可以說是近期最熱門的露營區之一，結合了泰雅族風格的豪華露營，能看到群山環繞的雲海，春天有美麗的富士櫻，夏季則有閃閃發光的營火蟲。而超大坪數的帆船帳篷也是這裡的亮點之一，設備齊全讓妞妞們直接拎包入住，附近還有張學良故居、清泉吊橋等知名景點可以參觀唷。");
	$("#people").val("4");
	$("#price").val("5888");
	$("#county").val("HSH");
	$("#startDate").val("2022-10-15");
	$("#endDate").val("2022-10-16");
	$("#score").val(5);
}


	// 	$(document).ready(function () { 		
	// 		// 新增貼文
	// 		$("#sub").click(function(){					
	// 			console.log($("#price").val());
	// 			if($.trim($("#title").val()) == ""){
	// 				alert("請輸入標題");
	// 				return;
	// 			}
	// 			if($.trim($("#postcontent").val()) == ""){
	// 				alert("請輸入內容");
	// 				return;
	// 			}
	// 			if($("#price").val() < 0 || $("#price").val() > 2147483647){
	// 				alert("輸入金額有誤");
	// 				return;
	// 			}
	// 			if($("#endDate").val() == "" && $("#startDate").val() !== "" || $("#endDate").val() !== "" && $("#startDate").val() == ""){
	// 				alert("需同時輸入開始露營日期及結束露營日期");
	// 				return;
	// 			}
	// 			if($("#endDate").val() < $("#startDate").val()){
	// 				alert("結束露營日期早於開始露營日期");
	// 				return;
	// 			}
				
	// 			var params = {
	// 				"title":$("#title").val(),
	// 				"content":$("#postcontent").val(),
	// 				"people":$("#people").val(),
	// 				"price":$("#price").val(),
	// 				"county":$("#county").val(),
	// 				"startdate":$("#startDate").val(),
	// 				"enddate":$("#endDate").val(),
	// 				"score":$("#score").val()
	// 				}
					
	// 			$.ajax({
	// 				type:"post",
	// 				url:"/morari/insertpost.controller",
	// 				dataType:"JSON",
	// 				contentType:"application/json",
	// 				data:JSON.stringify(params),
	// 				success: function(data){
	// 					if(data == true){
	// 						alert("新增成功");
	// 						window.location.href = "/morari/admin/forum/forumadminindex";
	// 					}
	// 				}
	// 			});	
	// 		});
				
	// 		// 顯示畫面
	// 		var peopleMax = 10;
	// 		for (let i = 1; i <= peopleMax; i++) {
	// 			var option = document.createElement("option");
	// 			option.value = i;
	// 			option.innerHTML = i;
	// 			$("#people").append(option);
	// 		}
			
	// 		var countyInner = ["台北市", "新北市", "基隆市", "桃園市", "新竹縣", "新竹市", "苗栗縣", "台中市", "彰化縣", "南投縣",
	// 			"雲林縣", "嘉義縣", "嘉義市", "台南市", "高雄市", "屏東縣", "宜蘭縣", "花蓮縣", "台東縣", "澎湖縣", "金門縣", "連江縣"];
	// 		var countyValue = ["TPE", "TPH", "KLU", "TYC", "HSH", "HSC", "MAL", "TXG", "CWH", "NTO", "YLH", "CHY", "CYI",
	// 			"TNN", "KHH", "IUH", "ILN", "HWA", "TTT", "PEH", "KMN", "LNN"];
	// 		for (let i = 0; i < countyInner.length; i++) {
	// 			var option = document.createElement("option");
	// 			option.value = countyValue[i];
	// 			option.innerHTML = countyInner[i];
	// 			$("#county").append(option);
	// 		}
			
	// 		var scoreMax = 5;
	// 		for (let i = 1; i <= scoreMax; i++) {
	// 			var option = document.createElement("option");
	// 			option.value = i;
	// 			option.innerHTML = i;
	// 			$("#score").append(option);
	// 		}
	// 	});


    // });
