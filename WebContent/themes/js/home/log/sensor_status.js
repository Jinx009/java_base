$(function() {
	$('#datepicker').val(new Date().Format('yyyy-MM-dd'));
	laydate.render({
		elem : '#datepicker',
	});
	_getLocation();
})
var _d = '';
function _getData() {
	var dateStr = $('#datepicker').val();
	var mac = $('#mac').val();
	var size = $('#size').val();
	var areaId = $('#area').val();
	var _data = {};
	_data.mac = mac;
	_data.dateStr = dateStr;
	_data.size = size;
	_data.areaId = areaId;
	$.ajax({
		url : '/d/log_sensor/status/1_0',
		dataType : 'json',
		data : JSON.stringify(_data),
		contentType : 'application/json;charSet=utf8',
		type : 'post',
		success : function(res) {
			for ( var i in res.data.list) {
				res.data.list[i].createTime = toDateTime(res.data.list[i].createTime);
				res.data.list[i].changeTime = toDateTime(res.data.list[i].changeTime);
				res.data.list[i].sendTime = toDateTime(res.data.list[i].sendTime);
			}
			if (_d == '') {
				_d = new Vue({
					el : '#data',
					data : {
						datas : res.data
					}
				})
			} else {
				_d.datas = res.data;
			}

		}
	})
}
var _area = '';
function _getArea() {
	var _locationId = $('#location').val();
	var _data = {};
	_data.locationId = _locationId;
	$.ajax({
		url : '/d/business_area/all/1_0',
		dataType : 'json',
		data : JSON.stringify(_data),
		contentType : 'application/json;charSet=utf8',
		type : 'post',
		success : function(res) {
			if(''==_area){
				_area = new Vue({
					el : '#area',
					data : {
						areas : res.data
					}
				})
			}else{
				_area.areas = res.data
			}
			_getData();
		}
	})
}

function _getLocation() {
	$.ajax({
		url : '/d/business_location/all/1_0',
		dataType : 'json',
		type : 'post',
		success : function(res) {
			new Vue({
				el : '#location',
				data : {
					locations : res.data
				}
			})
			_getArea();
		}
	})
}
