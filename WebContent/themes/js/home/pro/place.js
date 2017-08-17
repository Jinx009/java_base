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
		url:'/home/d/place',
		type:'post',
		dataType:'json',
		success:function(res){
			closeLoad(_i);
			if(res.data!=null){
				new Vue({
	   				  el: '#place',
	   				  data:{places:res.data.resultData.parkPlaceList}
	    		})
			}else{
				layer.alert('暂无数据！');
			}
		}
	})
}