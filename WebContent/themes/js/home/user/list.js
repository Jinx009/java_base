function getData(){
	$.ajax({
		url:'/home/d/web_user_list',
		type:'post',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				if(res.data!=null){
					for(var i in res.data){
						res.data[i].createTime = toDate(res.data[i].createTime);
					}
					new Vue({
		   				  el: '.data-tbody',
		   				  data:{data:res.data}
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
/**
 * 用户状态修改
 * @param status
 * @param id
 */
function changeStatus(status,id){
	var _text = '您确定要禁用该用户吗？';
	if('1'===status){
		_text = '您确定要激活该用户吗？';
	}
	layer.confirm(_text, {
	  btn: ['确定','取消'] 
	}, function(){
	   var params = 'status='+status+'&id='+id;
	   $.ajax({
		   url:'/home/d/web_user_status',
		   data:params,
		   type:'post',
		   dataType:'json',
		   success:function(res){
			   if('200'==res.code){
					layer.alert('操作成功！',function(){
						location.reload();
					});
				}else if('8001'==res.code){
					layer.alert('您无权限操作此项！');
				}else{
					layer.alert(res.msg);
				}
		   }
	   })
	});
}