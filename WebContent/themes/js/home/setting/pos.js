$(function(){
	_i = showLoad();
	getData();
	laydate.render({
		elem : '#birthday',
	});
})
var _i = -1;
function getData(){
	if(-1==_i){
		_i = showLoad();
	}
	$.ajax({
		url:'/home/d/mofang/user',
		type:'post',
		dataType:'json',
		success:function(res){
			closeLoad(_i);
			if(res.data!=null){
				new Vue({
	   				  el: '#account',
	   				  data:{accounts:res.data.data.users}
	    		})
			}else{
				layer.alert('暂无数据！');
			}
		}
	})
}
function _newUser(){
	_open_('3','/home/p/setting/pos','/home/p/setting/pos_add');
}
var _editId = '';
function _edit(_e){
	_editId = $(_e).attr('userId');
	$('#name').val($(_e).attr('name'));
	$('#birthday').val($(_e).attr('birthday'));
	$('#password').val($(_e).attr('password'));
	$('#email').val($(_e).attr('email'));
	$('#mobile').val($(_e).attr('mobile'));
	var _sex = $(_e).attr('sex');
	var _status = $(_e).attr('status');
	if(_status==='VALID'){
		var _htmlStr = '<option value="VALID" selected="selected"  >可用</option>';
		_htmlStr += '<option value="INVALID"  >禁用</option>';
		$('#status').html(_htmlStr);
	}else{
		var _htmlStr = '<option value="VALID"  >可用</option>';
		_htmlStr += '<option value="INVALID"  selected="selected" >禁用</option>';
		$('#status').html(_htmlStr);
	}
	if(_sex==='MALE'){
		var _htmlStr = '<option value="MALE" selected="selected"  >MALE</option>';
		_htmlStr += '<option value="INVALID"  >禁用</option>';
		$('#sex').html(_htmlStr);
	}else{
		var _htmlStr = '<option value="FEMALE"  >FEMALE</option>';
		_htmlStr += '<option value="FEMALE"  selected="selected" >FEMALE</option>';
		$('#sex').html(_htmlStr);
	}
	_showNew();
}
function _save(){
	var _userId = _editId,
		_name = $('#name').val(),
		_password = $('#password').val(),
		_birthday = $('#birthday').val(),
		_email = $('#email').val(),
		_mobile = $('#mobile').val(),
		_sex = $('#sex').val(),
		_status = $('#status').val();
	if(_sex==null||_sex==''||
	   _name==null||_name==''||
	   _email==null||_email==''||
	   _birthday==null||_birthday==''){
		layer.alert('请完善个人资料！');
	}else if(!/^1[3|4|5|8][0-9]\d{4,8}$/.test(_mobile)){
		layer.alert('手机号码不合法！');
	}else{
		var _params = 'mobile='+_mobile+'&sex='+_sex+'&status='+_status+'&userId='+_userId+'&name='+_name+'&birthday='+_birthday+'&email='+_email;
		if(_password!=null&&_password!=''){
			_params+= '&password='+_password;
		}
		$.ajax({
			url:'/home/d/mofang/update_user',
			dataType:'json',
			data:_params,
			type:'post',
			success:function(res){
				if('200'==res.code){
					layer.alert('修改成功！',function(){
						location.reload();
					})
				}
			}
		})
	}
}