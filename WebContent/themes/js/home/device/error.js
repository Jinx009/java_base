$(function(){
	_getLocation();
})
var _d = '',_area = '',_mac = '',_areaId = 0;
var  _nowPage = 0,_max = 0;
function _getData(_type,_index){
	var _data = {};
	_data.mac = $('#mac').val();
	_data.areaId = $('#area').val();
	if(_data.areaId==''||_data.areaId==null){
		_data.areaId = 0;
	}
	_data.p = _getPage(_type,_index);
	if(_data.mac!=_mac||_data.areaId!=_areaId){
		_mac = _data.mac;
		_areaId = _data.areaId;
		_data.p = 1;
	}
	if(_data.p!=-1){
		$.ajax({
			url:'/d/device_error_flow/list/1_0',
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
					res.data.list[i].deviceErrorFlow.logTime = toDateTime(res.data.list[i].deviceErrorFlow.logTime);
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
	var _data = {};
	_data.locationId = _locationId;
	$.ajax({
		url:'/d/business_area/all/1_0',
		dataType:'json',
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
			_getData(0,1);
		}
	})
}