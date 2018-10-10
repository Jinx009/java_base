$(function() {
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
/**
 * 登录
 */
function login() {
	var userName = $('#userName').val();
	var pwd = $('#pwd').val();
	var params = 'userName=' + userName + '&pwd=' + pwd;
	if (userName == null || '' == userName) {
		$('#errorMsg').html('用户名不能为空！')
	} else {
		if (pwd == null || '' == pwd) {
			$('#errorMsg').html('密码不能为空！')
		} else {
			$.ajax({
				url : '/paper/d/homeUser/login',
				type : 'post',
				data : params,
				dataType:'json',
				success : function(res) {
					if ('200' == res.code ) {
						setSessionStorage('_userType',res.data.role);
						location.href = '/paper/p/paper';
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