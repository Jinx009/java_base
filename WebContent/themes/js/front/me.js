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