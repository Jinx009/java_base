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
					if(res.data[i].toWorkStatus==1){
						res.data[i].toWorkTime = toDateTime(res.data[i].toWorkTime).split(' ')[1];
					}
					if(res.data[i].offWorkStatus==1){
						res.data[i].offWorkTime = toDateTime(res.data[i].offWorkTime).split(' ')[1];
					}
					res.data[i].workTime = parseFloat(res.data[i].workTime/60).toFixed(2)+'小时';
					res.data[i].createTime = toDate(res.data[i].createTime);
				}
				new Vue({
					el:'#data',
					data:{
						datas:res.data
					}
				})
			}
		}
	})
}