function _createJob(_cmd,_num){
	var _mac = $('#mac').text();
	var data1 ='',data2='',data3='',cmd='';
	cmd=_cmd;
	if('0'==_cmd){
		cmd = $('#fast_job').val();
	}else{
		data1 = $('#'+_cmd+'1').val();
		data2 = $('#'+_cmd+'2').val();
		data3 = $('#'+_cmd+'3').val();
	}
	params = 'cmd='+cmd+'&data1='+data1+'&data2='+data2+'&data3='+data3+'&mac='+_mac;
	$.ajax({
		url:'/iot/iot/sensor/job',
		type:'post',
		dataType:'json',
		data:params,
		success:function(res){
			if('200'==res.code){
				layer.alert('操作成功！',function(){
					_open('1','/p/device/job/list');
				})
			}else{
				layer.alert(res.msg);
			}
		}
	})
}
