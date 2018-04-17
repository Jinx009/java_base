$(function(){
	_getData();
})
function _getData(){
	$.ajax({
		url:'/home/cloud/accessControl/log?p=1',
		dataType:'json',
		type:'post',
		success:function(res){
			for(var i in res.data.data){
				res.data.data[i].create
			}
			new Vue({
				el:'#log',
				data:{
					logs:res.data.data
				}
			})
		}
	})
}