$(function(){
	_loadClass();
	_getData();
})

function _getData(){
	$.ajax({
		url:'/subcribe/d/order/list',
		dataType:'json',
		type:'get',
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