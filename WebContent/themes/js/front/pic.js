$(function(){
	_loadClass();
	_getData();
})
function _getData(){
	$.ajax({
		url:'/front/d/pro_pic/list',
		type:'get',
		dataType:'json',
		success:function(res){
			new Vue({
				el:'#data',
				data:{
					datas:res.data
				}
			})
		}
	})
}