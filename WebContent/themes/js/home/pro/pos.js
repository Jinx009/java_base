$(function(){
	_i = showLoad();
	_getArea();
	_getData();
})
var _i = -1;
function _getData(){
	if(-1==_i){
		_i = showLoad();
	}
	$.ajax({
		url:'/home/d/pos',
		type:'post',
		dataType:'json',
		success:function(res){
			closeLoad(_i);
			if('200'==res.code){
				if(res.data!=null){
					new Vue({
		   				  el: '#device',
		   				  data:{devices:res.data.resultData.devices}
		    		})
				}else{
					layer.alert('暂无数据！');
				}
			}else if('8001'==res.code){
				layer.alert('您无权限操作此项！');
			}else{
				layer.alert(res.msg);
			}
		}
	})
}
function _getArea(){
	$.ajax({
		url:'/home/d/area',
		type:'get',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				if(res.data!=null){
					new Vue({
		   				  el: '#area',
		   				  data:{areas:res.data}
		    		})
				}else{
					layer.alert('暂无数据！');
				}
			}else if('8001'==res.code){
				layer.alert('您无权限操作此项！');
			}else{
				layer.alert(res.msg);
			}
		}
	})
}
function _getStreet(){
	var _area = $('#area').val();
	$.ajax({
		url:'/home/d/streetByAreaId',
		type:'get',
		data:'areaId='+_area,
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				if(res.data!=null){
					new Vue({
		   				  el: '#street',
		   				  data:{streets:res.data}
		    		})
				}else{
					layer.alert('暂无数据！');
				}
			}else if('8001'==res.code){
				layer.alert('您无权限操作此项！');
			}else{
				layer.alert(res.msg);
			}
		}
	})
}