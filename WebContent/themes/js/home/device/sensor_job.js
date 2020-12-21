function _createJob(_cmd,_num){
	var _mac = $('#mac').val();
	var data1 ='',data2='',data3='',cmd='';
	cmd=_cmd;
	if('0'==_cmd){
		cmd = $('#fast_job').val();
	}else if('32'==_cmd){
		data1 = $('#'+_cmd+'1').val();
		var _d = {};
		_d.s10 =  $('#s10').val();
		_d.s11 =  $('#s11').val();
		_d.s12 =  $('#s12').val();
		_d.s13 =  $('#s13').val();
		_d.s14 =  $('#s14').val();
		_d.s15 =  $('#s15').val();
		_d.s16 =  $('#s16').val();
		_d.s17 =  $('#s17').val();
		_d.s18 =  $('#s18').val();
		_d.s19 =  $('#s19').val();
		_d.s1A =  $('#s1A').val();
		_d.s20 =  $('#s20').val();
		_d.s21 =  $('#s21').val();
		_d.s22 =  $('#s22').val();
		_d.s23 =  $('#s23').val();
		_d.s24 =  $('#s24').val();
		_d.s25 =  $('#s25').val();
		_d.s26 =  $('#s26').val();
		_d.s27 =  $('#s27').val();
		_d.s28 =  $('#s28').val();
		data2 = JSON.stringify(_d);
	}else{
		data1 = $('#'+_cmd+'1').val();
		data1 = data1.replace(/\+/g, "%2B");
    	data1 = data1.replace(/\&/g, "%26");
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
					_open('1','/p/device/job/list?mac='+_mac);
				})
			}else{
				layer.alert(res.msg);
			}
		}
	})
}
