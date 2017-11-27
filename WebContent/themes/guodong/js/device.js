$(function(){
	_getData();
	setInterval(function() {
		_getData();
	}, 100000);
})
var _d = '';
function _getData(){
	$.ajax({
		url:'/interface/gd/data',
		type:'get',
		dataType:'json',
		success:function(res){
			var _data = new Array();
			for(var i in res.data){
				_data[i] = {};
				var _status = res.data[i].data;
				var _s = _status.split('');
				if (_s[6] == '3' && _s[7] == '0'){
					_data[i].status = _s[_s.length - 1];
				}else if(_s[6] == '3' && _s[7] == '5'){
					_data[i].status = _s[15];
				}
				_data[i].time_s = res.data[i].time_s;
				_data[i].devEUI = res.data[i].devEUI;
				_data[i].data = res.data[i].data;
			}
			console.log(res.data)
			if(''==_d){
				_d = new Vue({
					el:'#data',
					data:{
						datas:_data
					}
				})
			}else{
				_d.datas = res.data
			}
		}
	})
}