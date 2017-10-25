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
				+ 'px;" class=" car none"  />';
		$('.location').append(_htmlStr);
	}
	run();

	setInterval(function() {

		run();

	}, 10000);
}
function run() {
	$.get('/interface/ja/view?areaId=17', function(res) {
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