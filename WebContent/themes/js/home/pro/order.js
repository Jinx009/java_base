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
		url:'/home/d/order',
		type:'post',
		dataType:'json',
		success:function(res){
			closeLoad(_i);
			if('200'==res.code){
				if(res.data!=null){
					var _d = res.data.data.products;
					for(var i in _d.data){
						_d.data[i].beginTime = toDateTime(_d.data[i].beginTime);
						if(_d.data[i].endTime!=null&&_d.data[i].endTime!=''){
							_d.data[i].endTime = toDateTime(_d.data[i].endTime);
						}else{
							_d.data[i].endTime = '';
						}
					}
					new Vue({
		   				  el: '#order',
		   				  data:{orders:res.data.data.products}
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