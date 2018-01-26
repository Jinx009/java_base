$(function(){
	_getData();
})
function _getData(){
	$.ajax({
		url:'/front/d/pro_book_post/list',
		type:'get',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				new Vue({
					el:'.content',
					data:{
						datas:res.data
					}
				})
			}
		}
	})
}
$(function(){
	_loadClass();
})