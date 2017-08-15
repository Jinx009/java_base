function getData(){
	$.ajax({
		url:'/back/d/active/user',
		type:'post',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				if(res.data!=null){
					new Vue({
		   				  el: '.data-tbody',
		   				  data:{data:res.data}
		    		})
				}else{
					layer.alert('暂无数据！');
				}
			}else{
				layer.alert(res.msg);
			}
		}
	})
}
function gos(id){
	location.href = '/back/d/exportUsers?activeId='+id;
}
$(function(){
	getData();
})