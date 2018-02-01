$(function(){
//	_session();
})
document.onkeydown = function(e) {
	if (!e) {
		e = window.event;
	}
	if ((e.keyCode || e.which) == 13) {
		login();
	}
}
function _session(){
	$.ajax({
		url:'/interface/ja/session',
		type:'get',
		dataType:'json',
		success:function(res){
			if('200'!=res.code){
				location.href = '/themes/ja/index.html';
			}
		}
	})
}

/**
 * 登录
 */
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
						location.href = '/themes/ja/lot.html'
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