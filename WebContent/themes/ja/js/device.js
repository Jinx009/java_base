$(function(){
	$.ajax({
		url:'/interface/ja/device',
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
							res.data.result[i].uid = resp.data.carNo;
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