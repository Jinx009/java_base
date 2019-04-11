/**
 * 键盘事件
 */
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
/**
 * 登陆
 */
function login() {
	var userName = $('#userName').val();
	var pwd = $('#pwd').val();
	var params = 'username=' + userName + '&pwd=' + pwd;
	if (userName == null || '' == userName) {
		$('#errorMsg').html('用户名不能为空！')
	} else {
		if (pwd == null || '' == pwd) {
			$('#errorMsg').html('密码不能为空！')
		} else {
			$.ajax({
				url : '/d/user/login',
				type : 'post',
				data : params,
				success : function(res) {
					if ('200' == res.code && '访问成功' == res.msg) {
						_open('1','/p/camera/list');
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