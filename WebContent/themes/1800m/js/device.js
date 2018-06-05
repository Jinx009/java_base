$(function(){
	$.ajax({
		url:'/interface/1800/device',
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
			_getSrc();
		}
	})
})
function _getSrc(){
	_getData2();
	setInterval(function() {

		_getData2();

	}, 5000);
}

function _getData2(){
	$.ajax({
		url:'/common/log/unicomFile',
		type:'get',
		dataType:'json',
		success:function(res){
			for(var i in res.data){
				if(res.data[i].indexOf('mac')<=0){
					var _mac = res.data[i].split(',')[0].split('\n')[1];
					$('#'+_mac+'_rsrp').html(res.data[i].split(',')[1]);
					$('#'+_mac+'_snr').html(res.data[i].split(',')[2]);
					$('#'+_mac+'_cellID').html(res.data[i].split(',')[3]);
				}
			}
		}
	})

}
function goLog(_element){
	if(!window.localStorage){
        alert('浏览器不支持localstorage');
        return false;
    }else{
        var storage = window.localStorage;
        storage['_mac'] = $(_element).html();
        location.href = '/themes/1800m/logs.html';
    }
}