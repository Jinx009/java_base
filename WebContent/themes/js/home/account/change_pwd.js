/**
 * 修改校验
 * @returns
 */
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
		doChangePwd(_oldPwd,_pwd);
	}
}
/**
 * 真实修改
 * @param _oldPwd
 * @param _pwd
 * @returns
 */
function doChangePwd(_oldPwd,_pwd){
	_oldPwd =  CryptoJS.MD5(_oldPwd);
	_pwd =  CryptoJS.MD5(_pwd);
	var params = 'oldPwd='+_oldPwd+'&pwd='+_pwd;
	$.ajax({
		url:'/home/d/user_pwd',
		type:'post',
		data:params,
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				layer.alert('操作成功！',function(){
					location.reload();
				});
			}else if('8001'==res.code){
				layer.alert('您无权限操作此项！');
			}else{
				layer.alert(res.msg);
			}
		}
	})
}