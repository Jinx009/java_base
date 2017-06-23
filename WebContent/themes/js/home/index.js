$(function() {
	changeWidth();
	$('.login-btn').bind('click', function() {
		login()
	});
	$('input').bind('click', function() {
		$('#errorMsg').html('')
	})
});
document.onkeydown = function(e) {
	if (!e) {
		e = window.event;
	}
	if ((e.keyCode || e.which) == 13) {
		login();
	}
}
window.onresize = function() {
	changeWidth()
};
function changeWidth() {
	var _width = $(window).width();
	$('.bg-table').css('margin-left', (_width - 500) / 2)
}
function login() {
	var userName = $('#userName').val();
	var pwd = $('#pwd').val();
	pwd = CryptoJS.MD5(pwd);
	var params = 'userName=' + userName + '&pwd=' + pwd;
	if (userName == null || '' == userName) {
		$('#errorMsg').html('用户名不能为空！')
	} else {
		if (pwd == null || '' == pwd) {
			$('#errorMsg').html('密码不能为空！')
		} else {
			$.ajax({
				url : '/home/config/login',
				type : 'post',
				data : params,
				success : function(res) {
					if ('200' == res.code && '访问成功' == res.msg) {
						location.href = '/home/p/lot'
					} else {
						$('#errorMsg').html(res.msg)
					}
				},
				error : function(res) {
					$('#errorMsg').html('系统异常')
				}
			})
		}
	}
};