$(function(){
	_i = showLoad();
	_getData();
})
var _i = -1;
function _getData(){
	if(-1==_i){
		_i = showLoad();
	}
	$.ajax({
		url:'/home/d/posDevice/list',
		type:'post',
		dataType:'json',
		success:function(res){
			closeLoad(_i);
			if('200'==res.code){
				if(res.data!=null){
					new Vue({
		   				  el: '#device',
		   				  data:{devices:res.data}
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
function _update(_e,_status){
	var _id = $(_e).attr('id');
	var _params = 'id='+_id+'&status='+_status;
	$.ajax({
		url:'/home/d/posDevice/updateStatus',
		type:'post',
		data:_params,
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				layer.alert('操作成功！',function(){
					location.reload();
				})
			}else{
				layer.alert(res.msg);
			}
		}
	})
}
