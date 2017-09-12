$(function(){
	_getData();
})
/**
 * 获取列表数据
 */
function _getData(){
	$.ajax({
		url:'/home/d/areaList',
		type:'GET',
		dataType:'json',
		success:function(res){
			console.log(res);
		}
	})
}
/**
 * 新增数据
 */
function _addArea(){
	
}