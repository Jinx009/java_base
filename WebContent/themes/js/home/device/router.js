$(function(){
	_getData();
})
var _d = '';
function _getData(){
	$.ajax({
		url:'/d/device_router/list/1_0',
		dataType:'json',
		type:'post',
		success:function(res){
			for(var i in res.data){
				res.data[i].status = 1;
				var timestamp = new Date().getTime();
				if((timestamp-res.data[i].lastSeenTime)>1000 * 120){
					res.data[i].status = 0;
				}
				res.data[i].lastSeenTime = toDateTime(res.data[i].lastSeenTime);
				
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