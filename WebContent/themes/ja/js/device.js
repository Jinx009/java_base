$(function(){
	_session();
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

function goLog(_element){
	if(!window.localStorage){
        alert('浏览器不支持localstorage');
        return false;
    }else{
        var storage = window.localStorage;
        storage['_mac'] = $(_element).html();
        location.href = '/themes/ja/logs.html';
    }
}