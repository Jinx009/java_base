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
		url:'/home/d/notice',
		type:'post',
		dataType:'json',
		success:function(res){
			closeLoad(_i);
			if('200'==res.code){
				if(res.data!=null){
					for(var i in res.data){
						res.data[i].createTime2 = toDate(res.data[i].createTime);
						res.data[i].createTime1 = toDateTime(res.data[i].createTime);
					}
					new Vue({
		   				  el: '#notice',
		   				  data:{notices:res.data}
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
function _change(_id,_status){
	var _params = 'id='+_id+'&status='+_status;
	$.ajax({
		url:'/home/d/updateNotice',
		data:_params,
		type:'post',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				layer.alert('操作成功!',function(){
					location.reload();
				})
			}
		}
	})
}