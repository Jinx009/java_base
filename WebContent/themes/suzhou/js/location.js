$(function(){
	_setMap();
})

function _setMap(){
	for(var i in _data){
		var _item = _data[i];
		var _htmlStr = '';
		if('x'==_item._type){
			_htmlStr = '<img src="/themes/img/A-car.png" width="30px;" style="position: absolute;top: '+_item._top+'px;left: '+_item._left+'px;"  />';
		}else{
			_htmlStr = '<img src="/themes/img/A-car-v.png" width="33px;" style="position: absolute;top: '+_item._top+'px;left: '+_item._left+'px;"  />';
		}
		$('.location').append(_htmlStr);
	}
}