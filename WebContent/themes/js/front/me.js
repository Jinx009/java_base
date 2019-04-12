$(function(){
	_userId = getLocalStorage('userId');
	if(_userId==''){
		setLocalStorage('_url','/f/p/me');
		location.href = '/f/p/register';
	}else{
		_getData();
	}
})
var _userId = '';
function _getData(){
	$.ajax({
		url:'/d/user/me?userId='+_userId,
		type:'get',
		dataType:'json',
		success:function(res){
			new Vue({
				el:'body',
				data:{
					user:res.data
				}
			})
		}
	})
}
function _save(){
	var _params = 'userId='+_userId+'&name='+$('#name').val()+'&pwd='+$('#pwd').val()+'&remarkB='+$('#remarkB').val();
	$.ajax({
		url:'/d/user/update',
		data:_params,
		type:'post',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				 layer.open({
	   				    content: '资料编辑成功！'
	   				    ,btn: '好',
	   				    yes:function(index){
	   				    	location.reload();
	   				    }
	   				  });
			}
		}
	})
}
function _out(){
	setLocalStorage('userId','');
	setLocalStorage('type','');
	setLocalStorage('_m','');
	setLocalStorage('_p','');
	setLocalStorage('_url','/f/p/me');
	location.href = '/f/login';
}