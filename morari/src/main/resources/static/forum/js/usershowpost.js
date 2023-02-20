let postid;
let commentnumber = 1;

//const fetch1 = fetch("/morari/forum/html/usershowpost.html").then(response => response.text());
const fetch1 = fetch("/morari/forum/html/userupdatepost.html").then(response => response.text());
const fetch2 = fetch("/morari/forum/html/usershowpostcomment.html").then(response => response.text());

Promise.all([fetch1, fetch2]).then(results => {
	const [html1, html2] = results;
	document.querySelector(".showpost").innerHTML = html1;
	document.querySelector(".showpostcomment").innerHTML = html2;
	// 執行您的 JS
	$(document).ready(function () {
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
				
				$("#releaseDate").val(data.releasedate);
				$("#userLike").val(data.userlike);
				$("#userUnlike").val(data.userunlike);
				
				if (data.posthide == 0) {
					var hidepost = "<button onclick='hidepost(" + postid + ")'>隱藏貼文</button>";
					$("#hidepost").append(hidepost);
				} else {
					var cancelhidepost = "<button onclick='cancelhidepost(" + postid + ")'>取消隱藏貼文</button>";
					$("#hidepost").append(cancelhidepost);
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

		// 顯示留言
		$.ajax({
			url: "/morari/showpostcommentbypostid.controller/" + postid,
			dataType: "JSON",
			contentType: "application/json",
			success: function (data) {
				if(data.length != 0){
					$.each(data, function (i, n) {
					// i=index 第幾個 n=element 元素
						var div = "<label># "+ commentnumber +"樓</label><br><input type='text' class='form-control' value='" + n.postcomment + "' style='width: 92%; float: left;' disabled>";
						if (n.postcommenthide == 0) {
							div += '<button onclick="hidepostcomment(' + n.postcommentid + ')" style="margin:0px 0px 0px 4px;">隱藏</button><br><br>';
						} else {
							div += '<button onclick="cancelhidepostcomment(' + n.postcommentid + ')" style="margin:0px 0px 0px 4px;">取消隱藏</button><br><br>';
						}
						$("#showpostcomment").append(div);
						commentnumber ++;
					});
				}else{
					var div = '<input type="text" class="form-control" name="releaseDate" id="releaseDate" value="暫時沒有留言" disabled><br>';
					$("#showpostcomment").append(div);
				}
			}
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
//				window.location.href = "/morari/forum/showpostbyuserid.controller";
				location.reload();
			}
		}
	});
}

function hidepost(id) {
	if (confirm("是否確定隱藏貼文?")) {
		$.ajax({
			type: "put",
			url: "/morari/hidepost.controller/" + id,
			dataType: "JSON",
			contentType: "application/json",
			success: function (data) {
				if (data == true) {
					alert("隱藏貼文成功");
					location.reload();
				}
			}
		});
	}
}

function cancelhidepost(id) {
	if (confirm("是否確定取消隱藏貼文?")) {
		$.ajax({
			type: "put",
			url: "/morari/cancelhidepost.controller/" + id,
			dataType: "JSON",
			contentType: "application/json",
			success: function (data) {
				if (data == true) {
					alert("取消隱藏貼文成功");
					location.reload();
				}
			}
		});
	}
}

function hidepostcomment(id) {
	if (confirm("是否確定隱藏留言?")) {
		$.ajax({
			type: "put",
			url: "/morari/hidepostcomment.controller/" + id,
			dataType: "JSON",
			contentType: "application/json",
			success: function (data) {
				if (data == true) {
					alert("隱藏留言成功");
					location.reload();
				}
			}
		});
	}
}

function cancelhidepostcomment(id) {
	if (confirm("是否確定取消隱藏留言?")) {
		$.ajax({
			type: "put",
			url: "/morari/cancelhidepostcomment.controller/" + id,
			dataType: "JSON",
			contentType: "application/json",
			success: function (data) {
				if (data == true) {
					alert("取消隱藏留言成功");
					location.reload();
				}
			}
		});
	}
}
