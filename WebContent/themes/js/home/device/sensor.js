$(function(){
	_getData(1);
})
var _d = '';
function _getData(_index){
	for(var i = 1;i<10;i++){
		$('#index'+i).removeClass('active');
	}
	$('#index'+_index).addClass('active');
	$.ajax({
		url:'/home/d/device/sensor',
		type:'post',
		dataType:'json',
		success:function(res){
			var _arr = [];
			for(var i in res.data.result){
				res.data.result[i].mac = res.data.result[i].mac + _index;
				res.data.result[i].description = parseInt(_index)*100+ parseInt(res.data.result[i].description);
				res.data.result[i].parentMac = res.data.result[i].parentMac+_index;
				res.data.result[i].routerMac = res.data.result[i].routerMac+_index;
				res.data.result[i].available = get();
				if(i<=19){
					_arr.push(res.data.result[i]);
				}
			}
			if(''==_d){
				_d = new Vue({
					el:'#data',
					data:{
						datas:_arr
					}
				})
			}else{
				_d.datas = _arr;
			}
		}
	})
}
function get(){
	var _s = Math.random();
	if(_s>0.5){
		return 1;
	}
	return 0;
}
