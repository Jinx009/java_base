$(function() {
	$('.login-btn').bind('click', function() {
		register()
	});
	$('input').bind('click', function() {
		$('#errorMsg').html('')
	})
});
/**
 * 登录
 */
function register() {
	var userName = $('#userName').val();
	var pwd = $('#pwd').val();
	var params = 'userName=' + userName + '&pwd=' + pwd +'&realName='+$('#realName').val();
	if (userName == null || '' == userName) {
		$('#errorMsg').html('用户名不能为空！')
	} else {
		if (pwd == null || '' == pwd) {
			$('#errorMsg').html('密码不能为空！')
		} else {
			$.ajax({
				url : '/paper/d/homeUser/register',
				type : 'post',
				data : params,
				success : function(res) {
					if ('200' == res.code) {
						layer.alert('Register Success',function(){
							location.href = '/paper/p/index';
						})
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