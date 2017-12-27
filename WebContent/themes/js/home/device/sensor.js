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
function _edit(_e){
	var _mac = $(_e).attr('mac').split('_mac')[1];
	var _desc = $('#'+_mac).val();
	var _params = 'mac='+_mac+'&desc='+_desc;
	$.ajax({
		url:'/home/d/device/sensor_update',
		type:'post',
		data:_params,
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				layer.alert('操作成功！',function(){
					location.reload();
				})
			}else{
				layer.alert(res.msg);
			}
		}
	})
}