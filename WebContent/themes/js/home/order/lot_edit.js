$(function(){
	_getData();
})
function _getData(){
	$.ajax({
		url:'/home/d/device/sensor',
		type:'post',
		dataType:'json',
		success:function(res){
			var _arr = [];
			var _lot = [];
			for(var i in res.data.result){
				for(var i=1;i<10;i++){
					var _i = i*100+ parseInt(res.data.result[i].description);
					_lot.push(_i);
				}
			}
			new Vue({
				el:'#data',
				data:{
					datas:_lot
				}
			})
		}
	})
}