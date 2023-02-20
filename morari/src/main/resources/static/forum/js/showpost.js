let url = window.location.href;
let arr = url.split('/');
let postid = arr[arr.length - 1];

let page = 1;
let commentnumber = 1;

let uid = 1;
		
const fetch1 = fetch("/morari/forum/html/showpost.html").then(response => response.text());
const fetch2 = fetch("/morari/forum/html/showpostcomment.html").then(response => response.text());

Promise.all([fetch1, fetch2]).then(results => {
	const [html1, html2] = results;
	document.querySelector(".showpost").innerHTML = html1;
	document.querySelector(".showpostcomment").innerHTML = html2;
	// 執行您的 JS
	$(document).ready(function () {

		// 顯示貼文
		$.ajax({
			url: "/morari/showpostbyid.controller/" + postid,
			dataType: "JSON",
			contentType: "application/json",
			success: function (data) {
				$("#title").val(data.title);
				$("#content").val(data.content);
				$("#user").val(data.uid);
				uid = data.uid;
				
				$("#picture").attr("src", data.picture);

				if (data.people > 0) {
					$("#people").val(data.people);
				}

				if (data.price > 0) {
					$("#price").val(data.price);
				}

				var countyInner = ["台北市", "新北市", "基隆市", "桃園市", "新竹縣", "新竹市", "苗栗縣", "台中市", "彰化縣", "南投縣",
					"雲林縣", "嘉義縣", "嘉義市", "台南市", "高雄市", "屏東縣", "宜蘭縣", "花蓮縣", "台東縣", "澎湖縣", "金門縣", "連江縣"];
				var countyValue = ["TPE", "TPH", "KLU", "TYC", "HSH", "HSC", "MAL", "TXG", "CWH", "NTO", "YLH", "CHY",
					"CYI", "TNN", "KHH", "IUH", "ILN", "HWA", "TTT", "PEH", "KMN", "LNN"];
				for (let i = 0; i < countyValue.length; i++) {
					if (countyValue[i] == data.county) {
						$("#county").val(countyInner[i]);
					}
				}

				if (data.startdate != "" && data.startdate != null) {
					$("#startDate").val(data.startdate);
				}

				if (data.enddate != "" && data.enddate != null) {
					$("#endDate").val(data.enddate);
				}

				if (data.score > 0) {
					$("#score").val(data.score);
				}

				$("#releaseDate").val(data.releasedate);

				$("#userLike").val(data.userlike);
				$("#userUnlike").val(data.userunlike);
			}
		});

		// 顯示留言
		$.ajax({
			url: "/morari/showpostcommentnonhidebypostid.controller/" + postid + "/" + page,
			dataType: "JSON",
			contentType: "application/json",
			success: function (data) {
				$.each(data, function (i, n) {
					// i=index 第幾個 n=element 元素
					var div = "<label># "+ commentnumber +"樓</label><br><input type='text' class='form-control' value='" + n.postcomment + "' style='width: 95%; float: left;' disabled>";
					div += "<button onclick='reportcomment(" + n.postcommentid + ")' style='float: right;'>檢舉</button><br><br>";
					$("#showpostcomment").append(div);
					commentnumber ++;
				});
				if(data.length == 3){
					var more = "<button onclick='showmore()'>顯示更多</button><br><br>";
					$("#showmore").append(more);
					page+=1;
				}
			}
		});

		// 檢舉貼文
		$("#sub").click(function () {
			if (confirm("是否確定檢舉貼文? 惡意檢舉貼文可能導致帳號被封鎖！")) {
				$.ajax({
					type: "put",
					url: "/morari/reportpost.controller/" + postid,
					dataType: "JSON",
					contentType: "application/json",
					success: function (data) {
						if (data == true) {
							alert("檢舉成功");
						}
					}
				});
			}
		});

		// 喜歡貼文
		$("#like").click(function () {
			$.ajax({
				type: "put",
				url: "/morari/likepost.controller/" + postid,
				dataType: "JSON",
				contentType: "application/json",
				success: function (data) {
					if (data == false) {
						alert("已喜歡過此貼文");
					}
					location.reload();
				}
			});
		});

		// 不喜歡貼文
		$("#unlike").click(function () {
			$.ajax({
				type: "put",
				url: "/morari/unlikepost.controller/" + postid,
				dataType: "JSON",
				contentType: "application/json",
				success: function (data) {
					if (data == false) {
						alert("已不喜歡過此貼文");
					}
					location.reload();
				}
			});
		});

		// 新增留言
		$("#sendcomment").click(function () {
			if ($.trim($("#postcomment").val()) == "") {
				alert("請輸入留言內容");
				return;
			}
			var comment = {
				"postcomment": $("#postcomment").val()
			};
			$.ajax({
				type: "post",
				url: "/morari/insertpostcomment.controller/" + postid,
				dataType: "JSON",
				contentType: "application/json",
				data: JSON.stringify(comment),
				success: function () {
					location.reload();
				}
			});
		});

	});

	});
	
function showcamper(){
	window.location.href = "/morari/camper/" + uid;
}

// 檢舉留言
function reportcomment(id){
	if (confirm("是否確定檢舉留言? 惡意檢舉留言可能導致帳號被封鎖！")) {
		$.ajax({
			type: "put",
			url: "/morari/reportpostcomment.controller/" + id,
			dataType: "JSON",
			contentType: "application/json",
			success: function (data) {
				if (data == true) {
					alert("檢舉成功");
				}
			}
		});
	}
}	

// 顯示更多留言
function showmore(){
	$.ajax({
		url: "/morari/showpostcommentnonhidebypostid.controller/" + postid + "/" + page,
		dataType: "JSON",
		contentType: "application/json",
		success: function (data) {
			$.each(data, function (i, n) {
				// i=index 第幾個 n=element 元素
				var div = "<label># "+ commentnumber +"樓</label><br><input type='text' class='form-control' value='" + n.postcomment + "' style='width: 95%; float: left;' disabled>";
				div += "<button onclick='reportcomment(" + n.postcommentid + ")' style='float: right;'>檢舉</button><br><br>";
				$("#showpostcomment").append(div);
				commentnumber ++;
			});
			if(data.length < 3){
				$("#showmore").empty("");
			}
			page+=1;
		}
	});
}