$(function() {
	_setMap();
})

function _setMap() {
	for ( var i in _data) {
		var _item = _data[i];
		var _htmlStr = '';
		_htmlStr = '<img id="'
				+ _item.lot
				+ '"  src="/themes/img/A-car.png" width="20px;" style="position: absolute;top: '
				+ _item._top + 'px;left: ' + _item._left
				+ 'px;" class=" car none" role="button" type="button"   />';
		$('.location').append(_htmlStr);
	}
	run();

	setInterval(function() {

		run();

	}, 15000);
}
function run() {
	$.get('/interface/wenzhou_modou/device', function(res) {
		var _num = 0;
		var params = res.data.result;
		for (idx in params) {
			var sensorVo = params[idx];
			if (sensorVo.available == 1) {
				$('#' + sensorVo.description).attr('data-toggle','popover');
				$('#' + sensorVo.description).attr('data-trigger','hover');
				$('#' + sensorVo.description).attr('data-container','body');
				if(parseInt(sensorVo.description)>=24){
					$('#' + sensorVo.description).attr('data-placement','right');
				}else if(parseInt(sensorVo.description)<=11){
					$('#' + sensorVo.description).attr('data-placement','left');
				}else{
					$('#' + sensorVo.description).attr('data-placement','bottom');
				}
				var _carNo = '未知',_bluetooth = '无';
				var _detail = '<b>驶入时间：</b>'+sensorVo.lastSeenTime+'</br><b>车位号：</b>'+sensorVo.description+'</br><b>Mac地址：</b>'+sensorVo.mac;
				if(sensorVo.bluetooth!=null&&''!=sensorVo.bluetooth){
					$.ajax({
						url:'/common/bluetooth/findByUuid?uuid='+sensorVo.bluetooth,
						type:'get',
						data:'json',
						async:false,
						success:function(res){
							if(res.data!=null){
								_bluetooth = sensorVo.bluetooth;
								_carNo = res.data.carNo;
								if('未知'!=_carNo&&'无'!=_bluetooth){
									_detail += '</br><b>蓝牙设备：</b>'+_bluetooth+'</br><b>车牌号码：</b>'+_carNo;
								}
							}
						}
					})
				}
				$('#' + sensorVo.description).attr('title','基本信息');
				$('#' + sensorVo.description).attr('data-content',_detail);
				$('#' + sensorVo.description).show();
				$("[data-toggle='popover']").popover({html: true});
			}else{
				if($('#' + sensorVo.description).length>0){
					$('#' + sensorVo.description).removeAttr('data-toggle');
					_num ++;
				}
				$('#' + sensorVo.description).hide();
			}
			$('#leftLot').html(_num);
		}
	})
}