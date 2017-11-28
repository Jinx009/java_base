$(function(){
	_getData(1);
})
var _d = '';
function _getData(_index){
	var _data = {};
	_data.p = _index;
	$.ajax({
		url:'/d/device_router/list/1_0',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
		type:'post',
		success:function(res){
			for(var i in res.data.list){
				res.data.list[i].status = 1;
				var timestamp = new Date().getTime();
				if((timestamp-res.data.list[i].lastSeenTime)>1000 * 120){
					res.data.list[i].status = 0;
				}
				res.data.list[i].lastSeenTime = toDateTime(res.data.list[i].lastSeenTime);
				
			}
			if(''==_d){
				_d = new Vue({
					el:'#datas',
					data:{
						datas:res.data
					}
				})
			}else{
				_d.datas = res.data
			}
		}
	})
}