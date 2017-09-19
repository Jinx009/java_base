$(function(){
	_getData();
})

function _getData(){
	$.ajax({
		url:'/home/d/pay',
		type:'get',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				new Vue({
					el:'#pay',
					data:{
						pays:res.data
					}
				})
			}
		}
	})
}

function _changeStatus(_id,_status){
	var _params = 'id='+_id+'&status='+_status;
	$.ajax({
		url:'/home/d/updatePayStatus',
		type:'post',
		dataType:'json',
		data:_params,
		success:function(res){
			if('200'==res.code){
				layer.alert('操作成功！',function(){
					location.reload();
				})
			}
		}
	})
}

function _del(_id){
	var _params = 'id='+_id;
	$.ajax({
		url:'/home/d/delPay',
		type:'post',
		dataType:'json',
		data:_params,
		success:function(res){
			if('200'==res.code){
				layer.alert('操作成功！',function(){
					location.reload();
				})
			}
		}
	})
}