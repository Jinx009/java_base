$(function(){
	_getLocation();
})
var _d = '';
function _getData(){
	var _data = {};
	_data.mac = $('#mac').val();
	_data.areaId = $('#area').val();
	$.ajax({
		url:'/d/device_sensor/all/1_0',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			for(var i in res.data.list){
				res.data.list[i].lastSeenTime = toDateTime(res.data.list[i].lastSeenTime);
			}
			if(''==_d){
				_d = new Vue({
					el:'#datas',
					data:{
						datas:res.data
					}
				})
			}else{
				_d.datas = res.data
			}
		}
	})
}

function _getArea(){
	var _locationId = $('#location').val();
	var _data = {};
	_data.locationId = _locationId;
	$.ajax({
		url:'/d/business_area/all/1_0',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			var _areaArray = new Array();
			_areaArray[0] = {};
			_areaArray[0].id = 0,_areaArray[0].name = '请选择area';
			if(_area==''){
				for(var i in res.data){
					_areaArray.push(res.data[i]);
				}
				_area = new Vue({
					el:'#area',
					data:{
						areas:_areaArray
					}
				})
			}else{
				_area.areas = res.data
			}
		}
	})
}

function _getLocation(){
	$.ajax({
		url:'/d/business_location/all/1_0',
		dataType:'json',
		type:'post',
		success:function(res){
			new Vue({
				el:'#location',
				data:{
					locations:res.data
				}
			})
			_getArea();
			_getData();
		}
	})
}