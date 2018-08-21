$(function(){
	_getData();
})
var drivers = [];
function _getData(){
	$.ajax({
		url:'/paper/d/paper/list',
		type:'post',
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
function _down(_e){
	var _f = $(_e).attr('_path');
	window.open('/paper'+_f,'_blank');
}
function _com(_e){
	var _id = $(_e).attr('_id');
	location.href = '/paper/p/result?id='+_id;
}
