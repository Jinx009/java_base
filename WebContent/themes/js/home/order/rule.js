function _newRole(){
	 _open_('4','/home/p/order/role','/home/p/order/rule_add');
}
$(function(){
	_getArea();
})
function _getArea(){
	$.ajax({
		url:'/home/d/area/list?id=26',
		type:'get',
		dataType:'json',
		success:function(res){
			if(res.data!=null){
				new Vue({
	   				  el: '#area',
	   				  data:{datas:res.data}
	    		})
			}
		}
	})
}