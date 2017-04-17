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