$(function(){
	$.ajax({
		url:'/interface/puruan/device',
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