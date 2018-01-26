$(function(){
	setSessionStorage('_class','icon-settings-');
	_getData();
	_loadClass();
})
function _getData(){
	$.ajax({
		url:'/front/d/pro_user/me',
		type:'get',
		dataType:'json',
		success:function(res){
			new Vue({
				el:'body',
				data:{
					item:res.data
				}
			})
		}
	})
}

function _edit(){
	var nickName = $('#nickName').val(),
		name = $('#name').val(),
		address = $('#address').val(),
		mobilePhone = $('#mobilePhone').val();
	var _params = 'name='+name+'&address='+address+'&nickName='+nickName+'&pwd='+pwd;
	$.ajax({
		url:'/front/d/pro_user/update',
		type:'post',
		dataType:'json',
		data:_params,
		success:function(res){
			if('200'==res.code){
				layer.open({
				    content: '操作成功！'
				    ,btn: '好'
				  });
			}
		}
	})
}