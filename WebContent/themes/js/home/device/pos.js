$(function(){
	_getData();
})

function _getData(){
	$.ajax({
		url:'/home/d/mofang/pos',
		type:'post',
		dataType:'json',
		success:function(res){
			new Vue({
				el:'#data',
				data:{
					datas:res.data.data.devices
				}
			})
		}
	})
}