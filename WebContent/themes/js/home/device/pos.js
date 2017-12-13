$(function(){
	_getData();
})

function _getData(){
	$.ajax({
		url:'/home/d/device/pos',
		type:'post',
		dataType:'json',
		success:function(res){
			for(var i in res.data.resultData.devices){
				res.data.resultData.devices[i].createTime = toDate(res.data.resultData.devices[i].createTime);
			}
			new Vue({
				el:'#data',
				data:{
					datas:res.data.resultData.devices
				}
			})
		}
	})
}