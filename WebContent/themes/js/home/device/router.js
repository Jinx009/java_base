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