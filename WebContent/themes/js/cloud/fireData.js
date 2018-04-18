$(function(){
	_getData();
})
function _getData(){
	$.ajax({
		url:'/home/cloud/fireControl/data',
		dataType:'json',
		type:'post',
		success:function(res){
			var _num = 0;
			for(var i in res.data){
				if(res.data[i].status==1){
					_num++;
				}
			}
			$('#num').html(_num);
			new Vue({
				el:'#log',
				data:{
					datas:res.data
				}
			})
		}
	})
}