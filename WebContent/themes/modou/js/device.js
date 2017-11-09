$(function(){
	$.ajax({
		url:'/interface/wenzhou_modou/device',
		type:'get',
		dataType:'json',
		success:function(res){
			for(var i in res.data.result){
				var _sensor = res.data.result[i];
				if(''!=_sensor.bluetooth&&_sensor.bluetooth!=null){
					$.ajax({
						url:'/common/bluetooth/findByUuid?uuid='+_sensor.bluetooth,
						type:'get',
						data:'json',
						async:false,
						success:function(resp){
							if(resp.data!=null){
								res.data.result[i].uid = resp.data.carNo;
							}
						}
					})
				}
			}
			new Vue({
				el:'#data',
				data:{
					datas:res.data.result
				}
			})
		}
	})
})
function goJob(_element){
	var _target = $(_element).attr('target');
	var _mac = $(_element).attr('mac');
	$.ajax({
		url:'/common/job/find?serviceName=modou',
		type:'get',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				layer.alert('尚有未完成任务！');
			}else{
				$.ajax({
					url:'/common/job/save/cfgsensor',
					type:'post',
					data:'target='+_target+'&mac='+_mac+'&serviceName=modou',
					dataType:'json',
					success:function(resp){
						if('200'==resp.code){
							layer.alert('正在校准中...',function(){
								location.href = '/thmems/modou/job.html';
							})
						}else{
							layer.alert(resp.msg);
						}
					}
				})
			}
		}
	})
}