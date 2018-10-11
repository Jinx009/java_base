$(function(){
	_i = showLoad();
	getData();
})
var _i = -1;
function getData(){
	if(-1==_i){
		_i = showLoad();
	}
	$.ajax({
		url:'/home/d/sensorInfoList',
		type:'post',
		dataType:'json',
		success:function(res){
			closeLoad(_i);
			if(res.data!=null){
				new Vue({
	   				  el: '#place',
	   				  data:{places:res.data}
	    		})
			}else{
				layer.alert('暂无数据！');
			}
		}
	})
}