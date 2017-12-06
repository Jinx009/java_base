function _createJob(_type){
	var _data = {};
	var _mac = $('#mac').text();
	_data.mac = _mac;
	if(_type=='reverseSensor'){//{"order":"F","data":"0","mac":"020000fffe000088"}
		_data.cmd = 'cfgsensor';
		_data.job_detail = {};
		_data.job_detail.data = '0';
		_data.job_detail.order = 'F';
		_data.job_detail.mac = _mac;
	}else if(_type=='adjustSensor'){//{"order":"R","data":"0","mac":"020000fffe000088"}
		_data.cmd = 'cfgsensor';
		_data.job_detail = {};
		_data.job_detail.data = '0';
		_data.job_detail.order = 'R';
		_data.job_detail.mac = _mac;
	}else if(_type=='resetSensor'){//{"mac":"020000fffe000088"}
		_data.cmd = 'rcvsensor';
		_data.job_detail = {};
		_data.job_detail.mac = _mac;
	}else if(_type=='restartSensor'){//{"mac":"020000fffe000088"}
		_data.cmd = 'rstsensor';
		_data.job_detail = {};
		_data.job_detail.mac = _mac;
	}else if(_type=='updateSensor'){//{"mac":"020000fffe000088"}
		var _version = $('#version').val();
		var _file = $('#files').val();
		if(_file=='0'||_version==''){
			layer.alert('参数不合法！');
			return;
		}
		_data.cmd = 'singleudsensor';
		_data.job_detail = {};
		_data.job_detail.mac = _mac;
		_data.job_detail.udurl = 'http://iot-admin.zhanway.com/uploads/version/'+_file;
		_data.job_detail.version = _version;
	}else if(_type=='heartUpdate'){//{"mac":"020000fffe000088","data":"32585"}
		var _heartTime = $('#heartTime').val();
		if(_heartTime==''){
			layer.alert('参数不合法！');
			return;
		}
		_data.cmd = 'sensorhbi';
		_data.job_detail = {};
		_data.job_detail.mac = _mac;
		_data.job_detail.data = _heartTime;
	}else if(_type=='cfgLock'){//{"data":0,"mac":"0001171026000002","type":"A"}
		var _lockType = $('#lockType').val();
		_data.cmd = 'cfglock';
		_data.job_detail = {};
		_data.job_detail.mac = _mac;
		_data.job_detail.type = 'A';
		_data.job_detail.data = parseInt(_lockType);
	}else if(_type=='lockTime'){//{"data":0,"mac":"0001171026000002","type":"B"}
		var _lockTime = $('#lockTime').val();
		_data.cmd = 'cfglock';
		_data.job_detail = {};
		_data.job_detail.mac = _mac;
		_data.job_detail.type = 'B';
		_data.job_detail.data = _lockTime;
	}
	$.ajax({
		url:'/d/device_job/create/1_0',
		type:'post',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
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
$(function(){
	_getFiles();
})
function _getFiles(){
	$.ajax({
		url:'/d/update_file/list/1_0',
			type:'post',
			dataType:'json',
			success:function(res){
				new Vue({
					el:'#files',
					data:{
						datas:res.data
					}
				})
			}
	})
}