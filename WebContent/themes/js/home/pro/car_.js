$(function() {
	var a = [ {
		area : 'A',
		lotNo : '0',
		x : '136',
		y : '256',
		direction :'',
		time:''
	}, {
		area : 'A',
		lotNo : '1',
		x : '136',
		y : '297',
		direction : '',
		time:''
	}, {
		area : 'A',
		lotNo : '2',
		x : '136',
		y : '340',
		direction : '',
		time:''
	}, {
		area : 'A',
		lotNo : '3',
		x : '136',
		y : '381',
		direction : '',
		time:''
	}, {
		area : 'A',
		lotNo : '4',
		x : '136',
		y : '424',
		direction : '',
		time:''
	}, {
		area : 'A',
		lotNo : '5',
		x : '136',
		y : '465',
		direction : '',
		time:''
	} ];
	buildMap(a);
	getData();
	setInterval(function() {

		getData();

	}, 60000);
})
function buildMap(baseData) {
	for ( var idx in baseData) {
		var lotNo = baseData[idx].lotNo;
		var x = baseData[idx].x;
		var y = baseData[idx].y;
		var direction = baseData[idx].direction;
		$('.location').append($('<div class="car" id="'+lotNo+'" role="button" type="button"  style="">').css({
			'top' : x + 'px',
			'left' : y + 'px'
		}).attr('data-ref', lotNo).attr('data-toggle','popover').attr('data-trigger','hover').attr('data-container','body').attr('data-placement','bottom').attr('title','基本信息'));
	}
	$("[data-toggle='popover']").popover({html: true});
}

function run(_data) {
	console.log(_data);
	var params = _data;
	var totalCnt = params.length;
	$('.block-value').text(totalCnt);
	for (idx in params) {
		var sensorVo = params[idx];
		var target = $('.location').find('[data-ref="' + sensorVo.lotNo + '"]');
		target.attr('data-content','<b>变动时间：</b>'+sensorVo.time+'</br><b>车位号：</b>'+sensorVo.lotNo+'</br><b>Mac地址：</b>'+sensorVo.mac);
		if (sensorVo.status == 1) {
			target.attr('data-toggle','popover')
			target.addClass('occupied-vertical');
		} else {
			target.removeClass('occupied-vertical');
			target.removeAttr('data-toggle');
		}
	}
}
var _arr;
function getData() {
	_arr = new Array(5)
	for(var i = 0;i<6;i++){
		_arr[i] = 0;
	}
	$.ajax({
		url:'/interface/gd/data',
		type:'get',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				for(var i = res.data.length-1;i>=0;i--){
					var _data = res.data[i];
					if(_data.devEUI=='0000000000004f90'&&_arr[0]==0){
						_setData(0,_data);
					}else if(_data.devEUI=='0000000000004f91'&&_arr[1]==0){
						_setData(1,_data);
					}else if(_data.devEUI=='0000000000004f92'&&_arr[2]==0){
						_setData(2,_data);
					}else if(_data.devEUI=='0000000000004f93'&&_arr[3]==0){
						_setData(3,_data);
					}else if(_data.devEUI=='0000000000004f94'&&_arr[4]==0){
						_setData(4,_data);
					}else if(_data.devEUI=='0000000000004f95'&&_arr[5]==0){
						_setData(5,_data);
					}
				}
				run(_arr);
			}
		}
	})
}
function _setData(_index,_data){
	_arr[_index] = {};
	_arr[_index].mac = _data.devEUI;
	var _status = _data.data;
	_arr[_index].time = '';
	var _s = _status.split('');
	_arr[_index].lotNo = _index;
	if (_s[6] == '3' && _s[7] == '0'){
		_arr[_index].time = _data.time_s;
		_arr[_index].status = _s[_s.length - 1];
	}else if(_s[6] == '3' && _s[7] == '5'){
		_arr[_index].status = _s[15];
	}
}
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
