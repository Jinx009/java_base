$(function(){
	_getData();
})
function _getData(){
	$.ajax({
		url:'/front/d/x/list',
		dataType:'json',
		type:'post',
		success:function(res){
			new Vue({
				el:'#data',
				data:{
					datas:res.data
				}
			})
		}
	})
}