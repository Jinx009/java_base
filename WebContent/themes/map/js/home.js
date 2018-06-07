$(function(){
	_getDoor();
	_getSensor();
})
function _getDoor(){
	$.ajax({
		url:'/home/cloud/accessControl/log?p=1',
		dataType:'json',
		type:'get',
		success:function(res){
			$('#doorNum').html(res.data.page.total);
		}
	})
}
function _getSensor(){
	$.ajax({
		url:'/interface/bearhunting/device',
		dataType:'json',
		type:'get',
		success:function(res){
			var _num = 0;
			for(var i in res.data.result){
				if(res.data.result[i].avaliable==1){
					_num++;
				}
			}
			$('#sensorNum').html(_num);
		}
	})
}