$(function(){
	_getData();
})
function _getData(){
	var _mac = $('#mac').text();
	var _data = {};
	_data.mac = _mac;
	$.ajax({
		url:'/d/device_router/detail/1_0',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			res.data.lastSeenTime = toDateTime(res.data.lastSeenTime);
			res.data.createTime = toDateTime(res.data.createTime);
			//_getLocation();
			new Vue({
				el:'#data',
				data:{
					datas:res.data
				}
			})
		}
	})
}

function _getLocation(){
	var _locationId = $('#location').val();
	$.ajax({
		url:'/d/business_location/all/1_0',
		dataType:'json',
		type:'post',
		success:function(res){
			var _htmlStr = '<option value="0" >请选择location</option>';
			for(var i in res.data){
				if(_locationId!=null&&_locationId!=''&&_locationId==res.data[i].id){
					_htmlStr += '<option value="'+res.data[i].id+'" selected="selected"  >'+res.data[i].name+'</option>';
				}else{
					_htmlStr += '<option value="'+res.data[i].id+'"  >'+res.data[i].name+'</option>';
				}
			}
			$('#locationId').html(_htmlStr);
		}
	})
}

function _edit(){
	var _mac = $('#mac').text();
	var _locationId = '';
	var _note = $('#note').val();
	var _data = {};
	_data.mac = _mac;
	_data.note = _note;
	_data.locationId = _locationId;
	$.ajax({
		url:'/d/device_router/edit/1_0',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
		type:'post',
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