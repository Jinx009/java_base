$(function(){
	_getData();
})
function _getData(){
	var _mac = $('#mac').text();
	var _data = {};
	_data.mac = _mac;
	$.ajax({
		url:'/d/device_cross_sensor/detail/1_0',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			res.data.updateTime = toDateTime(res.data.updateTime);
			res.data.createTime = toDateTime(res.data.createTime);
			new Vue({
				el:'#data',
				data:{
					datas:res.data
				}
			})
		}
	})
}