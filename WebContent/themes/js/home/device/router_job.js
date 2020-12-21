function _createJob(_type){
	var _data = {};
	var _mac = $('#mac').text();
	_data.mac = _mac;
	if(_type=='updateRouter'){
		var _version = $('#version').val();
		var _file = $('#files').val();
		if(_file=='0'||_version==''){
			layer.alert('参数不合法！');
			return;
		}
		_data.cmd = 'udarm';
		_data.job_detail = {};
		_data.job_detail.udurl = 'http://iot-admin.zhanway.com/uploads/version/'+_file;
		_data.job_detail.version = _version;
	}else if(_type=='batchUpdateSensor'){
		var _version = $('#version').val();
		var _file = $('#files').val();
		if(_file=='0'||_version==''){
			layer.alert('参数不合法！');
			return;
		}
		_data.cmd = 'batchudsensor';
		_data.job_detail = {};
		_data.job_detail.udurl = 'http://iot-admin.zhanway.com/uploads/version/'+_file;
		_data.job_detail.version = _version;
	}else if(_type=='uploadLog'){
		var _logDate = $('#logDate').val();
		_data.cmd = 'getarmlog';
		_data.job_detail = {};
		_data.job_detail.date = _logDate;
		_data.job_detail.udurl = 'ftp://wx.zhanway.com';
	}else if(_type=='batchUpload'){
		_data.cmd = 'sensorreport';
		_data.job_detail = {};
	}else if(_type=='rstArm'){
		_data.cmd = 'rstarm';
		_data.job_detail = {};
	}
	$.ajax({
		url:'/d/device_job/router_create/1_0',
		type:'post',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
		success:function(res){
			if('200'==res.code){
				layer.alert('操作成功！',function(){
					_open('1','/p/device/router/list');
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
				new Vue({
					el:'#files1',
					data:{
						datas:res.data
					}
				})
			}
	})
}