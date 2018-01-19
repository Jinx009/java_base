function _newRole(){
	 _open_('4','/home/p/order/role','/home/p/order/rule_add');
}
$(function(){
	_getData();
})
function _getData(){
	$.ajax({
		url:'/home/d/mofang/rule',
		type:'get',
		dataType:'json',
		success:function(res){
			if(res.data!=null){
				for(var i in res.data){
					res.data[i].createTime = toDate(res.data[i].createTime);
				}
				new Vue({
	   				  el: '#data',
	   				  data:{datas:res.data}
	    		})
			}
		}
	})
}