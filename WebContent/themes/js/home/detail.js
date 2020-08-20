$(function() {
	show('今日角度异常', 1);
})
var _d = '';
function show(_a, _b) {
	$('#selectTitle').html(_a);
	var url = '';
	if (_b == 1) {
		url='/iot/device/angleError';
	}
	if(_b==2){
		url = '/iot/device/dataError';
	}
	if(_b==3){
		url = '/iot/device/daysLost';
	}
	if(_b==4){
		url = '/iot/device/guangdongLost';
	}
	if(_b==5){
		url = '/iot/device/sichuanLost';
	}
	if(_b==6){
		url = '/iot/device/hubeiLost';
	}
	if(_b==7){
		url = '/iot/device/lost';
	}
	$.ajax({
		url :url,
		dataType : 'json',
		type : 'post',
		success : function(res) {
			for(var i in res.data){
				res.data[i].num = parseInt(i)+1;
				res.data[i].updateTime = toDateTime(res.data[i].updateTime);
				res.data[i].dataTime = toDateTime(res.data[i].dataTime);
			}
			if ('' == _d) {
				_d = new Vue({
					el : '#datas',
					data : {
						datas : res.data
					}
				})
			} else {
				_d.datas = res.data
			}
		}
	})
}
function goLost(){
	location.href = '/home/device/lost_list';
}
function goAngle(){
//	location.href = '/home/device/angle';
	layer.alert('研发中！');
}