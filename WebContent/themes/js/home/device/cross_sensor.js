$(function(){
	_getLocation()
})
var _d = '',_area = '',_mac = '',_areaId = 0,_locationId = 0;
var  _nowPage = 0,_max = 0;
function _getData(_type,_index){
	var _data = {};
	_data.mac = $('#mac').val();
	_data.areaId = $('#area').val();
	_data.locationId = $('#location').val();
	if(_data.areaId==''||_data.areaId==null){
		_data.areaId = 0;
	}
	if(_data.locationId==''||_data.locationId==null){
		_data.locationId = 0;
	}
	_data.p = _getPage(_type,_index);
	if(_data.mac!=_mac||_data.areaId!=_areaId||_data.locationId!=_locationId){
		_mac = _data.mac;
		_areaId = _data.areaId;
		_locationId = _data.locationId;
		_data.p = 1;
	}
	if(_data.p!=-1){
		$.ajax({
			url:'/d/device_cross_sensor/list/1_0',
			dataType:'json',
			data:JSON.stringify(_data),
			contentType:'application/json;charSet=utf8',
			type:'post',
			success:function(res){
				$('#_end').bind('click',function(){
					_getData(0,res.data.page.pages);
				})
				_max = res.data.page.pages;
				for(var i in res.data.list){
					res.data.list[i].updateTime = toDateTime(res.data.list[i].updateTime);
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
}


function _getArea(){
	var _locationId = $('#location').val();
	if(0==_locationId){
		var _areaArray = new Array();
		_areaArray[0] = {};
		_areaArray[0].id = 0,_areaArray[0].name = '请选择area';
		if(_area==''){
			_area = new Vue({
				el:'#area',
				data:{
					areas:_areaArray
				}
			})
		}else{
			_area.areas = _areaArray
		}
	}else{
		var _data = {};
		_data.locationId = _locationId;
		$.ajax({
			url:'/d/business_area/all/1_0',
			dataType:'json',
			data:JSON.stringify(_data),
			contentType:'application/json;charSet=utf8',
			type:'post',
			success:function(res){
				_area.areas = res.data
			}
		})
	}
}

function _getLocation(){
	$.ajax({
		url:'/d/business_location/all/1_0',
		dataType:'json',
		type:'post',
		success:function(res){
			var _locationArray = new Array();
			_locationArray[0] = {};
			_locationArray[0].id = 0,_locationArray[0].name = '请选择location';
			for(var i in res.data){
				_locationArray.push(res.data[i]);
			}
			new Vue({
				el:'#location',
				data:{
					locations:_locationArray
				}
			})
			_getArea();
			_getData(0,1);
		}
	})
}

function _goJob(_e){
	var _mac = $(_e).attr('id').split('_j')[1];
	_open_('1','/p/device/cross_sensor/list','/p/device/cross_sensor/job?mac='+_mac);
}

function _goDetail(_e){
	var _mac = $(_e).attr('id').split('_d')[1];
	_open_('1','/p/device/cross_sensor/list','/p/device/cross_sensor/detail?mac='+_mac);
}