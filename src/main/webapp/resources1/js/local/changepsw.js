$(function() {
	// 修改平台密码的controller url
	var url = '/o2o/local/changelocalpwd';
	// 从地址栏的URL里获取usertype
	// usertype=1则为customer,其余为shopowner
	var usertype = getQueryString('usertype');
	$('#submit').click(function() {
		// 获取帐号
		var userName = $('#userName').val();
		// 获取原密码
		var password = $('#password').val();
		// 获取新密码
		var newPassword = $('#newPassword').val();
		var confirmPassword = $('#confirmPassword').val();
		if (newPassword != confirmPassword) {
			$.toast('两次输入的新密码不一致！');
			return;
		}
		// 添加表单数据
		var formData = new FormData();
		formData.append('userName', userName);
		formData.append('password', password);
		formData.append('newPassword', newPassword);
		// 获取验证码
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		// 将参数post到后台去修改密码
		$.ajax({
			url : url,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					if (usertype == 1) {
						// 若用户在前端展示系统页面则自动退回到前端展示系统首页
						window.location.href = '/o2o/frontend/index';
					} else {
						// 若用户是在店家管理系统页面则自动回退到店铺列表页中
						window.location.href = '/o2o/shopadmin/shoplist';
					}
				} else {
					$.toast('提交失败！' + data.errMsg);
					$('#captcha_img').click();
				}
			}
		});
	});

	$('#back').click(function() {
		window.location.href = '/o2o/shopadmin/shoplist';
	});
});
