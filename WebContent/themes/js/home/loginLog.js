$(function(){
	_getData();
})
var drivers = [];
function _getData(){
	$.ajax({
		url:'/paper/d/homeUserLoginLog/list',
		type:'post',
		dataType:'json',
		success:function(res){
			for(var i in res.data){
				res.data[i].createTime = toDateTime(res.data[i].createTime);
			}
			new Vue({
				el:'body',
				data:{
					datas:res.data
				}
			})
		}
	})
}