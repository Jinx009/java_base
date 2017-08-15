function getData(){
	$.ajax({
		url:'/back/d/activeList',
		type:'post',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				if(res.data!=null){
					for(var i in res.data){
						res.data[i].url = 'http://127.0.0.1:9002/index?_key='+res.data[i].keyword;
					}
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
	window.open('/home/p/active/user?activeId='+id);
}
$(function(){
	getData();
})