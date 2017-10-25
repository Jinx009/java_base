$(function(){
	$.ajax({
		url:'/interface/ja/device',
		type:'get',
		dataType:'json',
		success:function(res){
			new Vue({
				el:'#data',
				data:{
					datas:res.data.result
				}
			})
		}
	})
})