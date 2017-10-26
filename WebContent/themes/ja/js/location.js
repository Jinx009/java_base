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

	}, 10000);
}
function run() {
	$.get('/interface/ja/device', function(res) {
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
				$('#' + sensorVo.description).attr('title','基本信息');
				$('#' + sensorVo.description).attr('data-content','<b>驶入时间：</b>'+sensorVo.lastSeenTime+'</br><b>车位号：</b>'+sensorVo.description+'</br><b>车牌号：</b>');
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