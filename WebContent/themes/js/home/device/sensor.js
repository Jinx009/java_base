$(function(){
	_getData();
})

function _getData(){
	$.ajax({
		url:'/home/d/device/sensor',
		type:'post',
		dataType:'json',
		success:function(res){
			new Vue({
				el:'#data',
				data:{
					datas:res.data.result
				}
			})
		}
	})
}