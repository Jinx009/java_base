function save(){
	var code = $('#code').val();
	var mobilePhone = $('#mobilePhone').val();
	var name = $('#name').val();
	var email = $('#email').val();
	var address = $('#address').val();
	var withNum = $('#withNum').val();
	var activeId = $('#activeId').val();
	var params = 'name='+name+'&email='+email+'&mobilePhone='+mobilePhone+'&address='+address+'&withNum='+withNum+'&activeId='+activeId;
	var telReg = /^0?1[3|4|5|7|8][0-9]\d{8}$/; //手机号正则13,14,15,17,18
	var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(!telReg.test(mobilePhone)){
		layer.open({content: '手机号码格式不正确！',btn: '我知道了'});
	}else if(!emailReg.test(email)){
		layer.open({content: '邮箱格式不正确！',btn: '我知道了'});
	}else if(name==null||name==''){
		layer.open({content: '观众姓名未填写！',btn: '我知道了'});
	}else if(address==null||address==''){
		layer.open({content: '联系地址未填写！',btn: '我知道了'});
	}else if(_code!=code){
		layer.open({content: '验证码不正确！',btn: '我知道了'});
	}else{
		$.ajax({
			url:'/front/d/saveUser',
			type:'post',
			data:params,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					 $('#_alert').show();
				}else{
					layer.open({content: '该手机号码已经报名过了！',btn: '我知道了'});
				}
			}
		})
	}
}
var _code = 1234;
$(function(){
	getCode();
})
function getCode(){
	var code = Math.floor(Math.random()*9000)+1000;
	_code = code;
	$('#_code').html(code);
}
function reset(){
	$('#mobilePhone').val('');
	$('#name').val('');
	$('#email').val('');
	$('#address').val('');
	$('#withNum').val('');
}