function changePwd(){
	var _oldPwd = $('#oldPwd').val();
	var _pwd = $('#pwd').val();
	var _rePwd = $('#rePwd').val();
	if(_oldPwd==null||_oldPwd==''){
		$('#errorMsg').html('原始密码不能为空！');
	}else if(_pwd==null||_pwd==''){
		$('#errorMsg').html('新密码不能为空！');
	}else if(_rePwd==null||_rePwd==''){
		$('#errorMsg').html('确认密码不能为空！');
	}else if(_pwd!=_rePwd){
		$('#errorMsg').html('两次密码不一致！');
	}else{
		
	}
}