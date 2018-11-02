$(function(){
	$('#datepicker').datepicker({
      autoclose: true,
      format: 'yyyy-mm-dd',
      lang: 'ch'
    });
	_getData();
})

function _getData(){
	$.ajax({
		url:'/home/d/attendanceRecord',
		type:'get',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				for(var i in res.data){
					
				}
				new Vue({
					el:'#data',
					data:{
						datas:res.data.data.operationLogs
					}
				})
			}
		}
	})
}