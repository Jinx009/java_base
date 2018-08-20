function register() {
	var _mobilePhone = $('#mobilePhone').val();
	var _password = $('#password').val();
	var plateNumber = $('#plateNumber').val();
	if (_mobilePhone == null || _mobilePhone == ''
			|| !isPoneAvailable(_mobilePhone)) {
		layer.open({
			content : '手机号码不正确！',
			skin : 'msg',
			time : 2
		// 2秒后自动关闭
		});
	} else if (_password == null || _password == '') {
		layer.open({
			content : '密码不能为空！',
			skin : 'msg',
			time : 2
		// 2秒后自动关闭
		});
	} else if (!isVehicleNumber(plateNumber)) {
		layer.open({
			content : '车牌号码不合规！',
			skin : 'msg',
			time : 2
		// 2秒后自动关闭
		});
	} else {
		var name = $('#name').val();
		$.ajax({
			url : '/subcribe/d/user/register',
			data : 'mobilePhone=' + _mobilePhone + '&pwd=' + _password
					+ '&name=' + name + '&plateNumber=' + plateNumber,
			dataType : 'json',
			type : 'post',
			success : function(res) {
				if ('200' == res.code) {
					layer.open({
						content : '注册成功',
						btn : '好',
						yes : function() {
							location.href = '/subcribe/p/login';
						}
					});
				} else {
					layer.open({
						content : res.msg,
						btn : '好'
					});
				}
			}
		})
	}
}
function isVehicleNumber(vehicleNumber) {
	var result = false;
	if (vehicleNumber.length == 7) {
		var express = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;
		result = express.test(vehicleNumber);
	}
	return result;
}
function isPoneAvailable(pone) {
	var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
	if (!myreg.test(pone)) {
		return false;
	} else {
		return true;
	}
}
