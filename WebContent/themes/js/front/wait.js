$(function(){
	setSessionStorage('_class','icon-home-');
	_loadClass();
	_getData();
})
function _getData(){
	$.ajax({
		url:'/front/d/pro_task/waitList',
		type:'get',
		dataType:'json',
		success:function(res){
			new Vue({
				el:'body',
				data:{
					datas:res.data
				}
			})
		}
	})
}
function _go(_e){
	var _id = $(_e).attr('id');
	location.href = '/front/p/detail?id='+_id;
}
