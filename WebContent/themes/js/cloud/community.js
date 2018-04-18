$(function(){
	_getData();
})
function _getData(){
	$.ajax({
		url:'/home/cloud/accessControl/person?p=1',
		dataType:'json',
		type:'post',
		success:function(res){
			for(var i in res.data.list){
				res.data.list[i].areaName = res.data.list[i].areaName.split('前门')[0];
			}
			new Vue({
				el:'#log',
				data:{
					persons:res.data.list
				}
			})
		}
	})
}