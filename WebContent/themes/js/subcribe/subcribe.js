$(function() {
	_loadClass();
	$("#my-input").calendar({
		minDate : _date
	});
	_date = _newDate();
	_getUser();
	_getHour();
	_getParkData();
})
var _m = '';
var _date = _newDate_1();
function check() {
	var _year = $('.picker-calendar-day-selected').attr('data-year');
	var _month = $('.picker-calendar-day-selected').attr('data-month');
	var _day = $('.picker-calendar-day-selected').attr('data-day');
	_month = parseInt(_month) + 1;
	if (_month < 10) {
		_month = '0' + _month;
	}
	_date = _year + '-' + _month + '-' + _day;
	_getData();
}
function _getParkData(){
	$.ajax({
		url:'/subcribe/d/place/list',
		dataType:'json',
		type:'get',
		success:function(res){
			new Vue({
				el:'#parkId',
				data:{
					parks:res.data
				}
			})
			_getData();
		}
	})
}
function _getUser(){
	$.ajax({
		url:'/subcribe/d/user/sessionUser',
		dataType:'json',
		type:'get',
		success:function(res){
			_m = res.data.mobilePhone;
			$('#plateNumber').val(res.data.plateNumber);
		}
	})
}
function _getHour(){
	var _h = $('#startHour').val();
	_h = parseInt(_h);
	var _l = 24 - _h;
	var _html = '';
	for(var i = 1;i<=_l;i++){
		_html+= '<option value="'+i+'" >'+i+'小时</option>'
	}
	$('#hours').html(_html);
}
function _getData(){
	var _parkId = $('#parkId').val(),_startHour = parseInt($('#startHour').val());
	var _t = parseInt($('#hours').val());
	var _endHour = _startHour+_t;
	$.ajax({
		url:'/subcribe/d/order/left',
		data:'parkId='+_parkId+'&startHour='+_startHour+'&endHour='+_endHour+'&dateStr='+_date,
		dataType:'json',
		type:'post',
		success:function(res){
			$('#desc').val('总车位：'+res.data.total+'个'+';剩余：'+res.data.left+'个');
		}
	})
}
function isVehicleNumber(vehicleNumber) {
	var result = false;
	if (vehicleNumber.length == 7) {
		var express = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;
		result = express.test(vehicleNumber);
	}
	return result;
}
function _save(){
	var _parkId = $('#parkId').val(),_startHour = parseInt($('#startHour').val());
	var _t = parseInt($('#hours').val());
	var _endHour = _startHour+_t;
	var _plateNumber = $('#plateNumber').val();
	if(!isVehicleNumber(_plateNumber)){
		layer.open({
			content : '车牌号码不合规！',
			skin : 'msg',
			time : 2
		// 2秒后自动关闭
		});
	}else{
		$.ajax({
			url:'/subcribe/d/order/save',
			data:'parkId='+_parkId+'&startHour='+_startHour+'&endHour='+_endHour+'&dateStr='+_date+'&plateNumber='+_plateNumber+"&mobilePhone="+_m,
			dataType:'json',
			type:'post',
			success:function(res){
				if('200'==res.code){
					layer.open({
						content : '预约成功',
						btn : '好',
						yes : function() {
							  _open('icon-star-','/subcribe/p/order');
						}
					});
				}else{
					layer.open({
						content :res.msg,
						btn : '好',
					});
				}
			}
		})
	}
}