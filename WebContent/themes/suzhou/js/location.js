$(function() {
	_setMap();
})

function _setMap() {
	for ( var i in _data) {
		var _item = _data[i];
		var _htmlStr = '';
		if ('x' == _item._type) {
			_htmlStr = '<img id="'
					+ _item.lot
					+ '"  src="/themes/img/A-car.png" width="30px;" style="position: absolute;top: '
					+ _item._top + 'px;left: ' + _item._left
					+ 'px;" class="none"  />';
		} else {
			_htmlStr = '<img id="'
					+ _item.lot
					+ '" src="/themes/img/A-car-v.png" height="30px;" style="position: absolute;top: '
					+ _item._top + 'px;left: ' + _item._left
					+ 'px;"  class="none"  />';
		}
		$('.location').append(_htmlStr);
	}
	run();

	setInterval(function() {

		run();

	}, 10000);
}
function run() {
	$.get('/interface/suzhou/view?areaId=19', function(res) {
		var _num = 0;
		var params = res.data;
		for (idx in params) {
			var sensorVo = params[idx];
			if (sensorVo.available == 1) {
				$('#' + sensorVo.addr).show();
			}else{
				if($('#' + sensorVo.addr).length>0){
					_num ++;
				}
				$('#' + sensorVo.addr).hide();
			}
			$('#leftLot').html(_num);
		}
	})
}