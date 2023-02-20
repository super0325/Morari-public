// 載入 你的.html
$(document).ready(function() {
	// let uid;
	fetch("/morari/utils/getuid")
		.then(response => response.text())
		.then(data => {
			document.getElementById("uid").value = data;
		})

	// 一鍵輸入 
	$("#fastinput").click(function() {
		fastinport();
	});

	$("#send").click(function() {
		function getFormData($form) {
			var unindexed_array = $form.serializeArray();
			var indexed_array = {};

			$.map(unindexed_array, function(n, i) {
				indexed_array[n['name']] = n['value'];
			});
			return indexed_array;
		}

		$.ajax({
			type: 'POST',
			url: '/morari/guest/work/resumeInsert.controller',
			contentType: 'application/json',
			data: JSON.stringify(getFormData($("#insert"))),
			success: function(response) {
				alert(response)
				window.location.href = "/morari/guest/work/resume.controller"
			}

		});
	});
});

function fastinport() {
	$("#name").val("陳昱成");
	$("#age").val("85");
	$("#mail").val("hcc8462@gmail.com");
	$("#phone").val("0955815656");
	$("#gender").attr('checked', true);
	$("#educational").val("大同科技大學精密機械工程學系學系");
	$("#skill").val("多益700分，日文n3");

}