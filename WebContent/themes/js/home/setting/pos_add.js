$(function() {
	_getArea();
	laydate.render({
		elem : '#birthday',
	});
})
function _getArea() {
	$.ajax({
		url : '/home/d/area/list?id=28',
		type : 'get',
		dataType : 'json',
		success : function(res) {
			if (res.data != null) {
				new Vue({
					el : '#area',
					data : {
						datas : res.data
					}
				})
			}
		}
	})
}
function _save(){
	var _sex = $('#sex').val(),
	_name = $('#name').val(),
	_mobile = $('#mobile').val(),
	_password = $('#password').val(),
	_email = $('#email').val(),
	_birthday = $('#birthday').val();
	var params = 'name='+_name+'&sex='+_sex+'&mobilePhone='+_mobile+'&password='+_password+'&email='+_email+'&birthday='+_birthday;
	if(_sex==null||_sex==''||
	   _name==null||_name==''||
	   _password==null||_password==''||
	   _email==null||_email==''||
	   _birthday==null||_birthday==''){
		layer.alert('请完善个人资料！');
	}else if(!/^1[3|4|5|8][0-9]\d{4,8}$/.test(_mobile)){
		layer.alert('手机号码不合法！');
	}else{
		$.ajax({
			url:'/home/d/mofang/add_user',
			type:'post',
			data:params,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					layer.alert('操作成功！',function(){
						_open('3','/home/p/setting/pos');
					})
				}
			}
		})
	}
	
	
}