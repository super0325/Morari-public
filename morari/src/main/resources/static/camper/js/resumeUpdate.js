// 載入 你的.html
let uid;
$(document).ready(function() {

	fetch("/morari/utils/getuid")
		.then(response => response.text())
		.then(data => {
			// console.log(data)
			uid = data;
		}).then(() => {

			$.ajax({
				type: 'GET',
				url: '/morari/guest/work/guestSelectResume.controller/' + uid,
				contentType: 'application/json',
				success: function(data) {
					$('#1').val(data.userprofiles.uid);
					$('#2').val(data.name);
					$('#3').val(data.age);
					$('#6').val(data.mail);
					$('#7').val(data.phone);
					$('#8').val(data.educational);
					$('#9').val(data.skill);

					switch (data.gender) {
						case '男':
							$("#4").attr('checked', true)
							break;
						case '女':
							$("#5").attr('checked', true)
							break;
					}
					
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
							type: 'Put',
							url: '/morari/guest/work/resumeUpdate.controller/' + data.number,
							contentType: 'application/json',
							data: JSON.stringify(getFormData($("#update"))),
							dataType:'text',
							success: function(response) {
							alert(response)
							window.location.href="/morari/guest/work/resume.controller"
								
							}
						});
					});
				}
			})
		})


})