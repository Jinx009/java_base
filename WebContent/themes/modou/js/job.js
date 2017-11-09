$(function(){
	$.ajax({
		url:'/common/job/all?serviceName=modou',
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
})