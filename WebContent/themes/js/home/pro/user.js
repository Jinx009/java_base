$(function(){
	_getData();
})

function _getData(){
	$.ajax({
		url:'/home/d/user',
		type:'get',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				new Vue({
					el:'#user',
					data:{
						users:res.data
					}
				})
			}
		}
	})
}

function _black(_id,_status){
	$.ajax({
		url:'/home/d/updateUserBlack',
		type:'post',
		data:'id='+_id+'&black='+_status,
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

function _month(_id,_status){
	$.ajax({
		url:'/home/d/updateUserSpecial',
		type:'post',
		data:'id='+_id+'&status='+_status,
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

function _goMonth(_id){
	_open_('6','/home/p/user','/home/p/monthSet?_id='+_id);
}