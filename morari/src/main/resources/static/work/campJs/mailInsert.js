// 載入 你的.html
$(document).ready(function() {
	var url = window.location.href;
	var id = url.split("/").pop();

	// 一鍵輸入 
	$("#fastinput").click(function() {
		fastinport();
	});
	$.ajax({
		type: 'POST',
		url: '/morari/admin/resume/selectNumber.controller/' + id,
		dataType: 'json',
		success: function(data) {
			$('#mail').val(data.mail);
		}
	});


	//文字編譯器
	//	ClassicEditor.create(document.querySelector('#editor'))
	//		.then(editor => {
	//			console.log(editor);
	//		}).catch(error => {
	//			console.error(error);
	//		});
	$("#sendMail").submit(function(event) {
		event.preventDefault();
		$.ajax({
			type: "post",
			url: "/morari/admin/user/work/userMail.controller",
			data: $(this).serialize(),
			datatype: 'TEXT',
			success: function(response) {
				if (confirm("確定寄出嗎?")) {
					alert(response);
					window.location.href = '/morari/admin/user/work/startCrud.controller';
				}
			}
		});
	});


});
function fastinport() {
	$("#subject").val("面試通知");
	$("#editor").val("您好，請問3/5下午2點方便面試嗎?");
}
