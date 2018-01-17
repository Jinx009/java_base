$(function(){
	$('#datepicker').datepicker({
		autoclose : true,
		format : 'yyyy-mm-dd',
		lang : 'ch'
	});
	$('#datepicker').val(new Date().Format('yyyy-MM-dd'));
	_getData();
//	setInterval(function() {
//
//		_getData();
//
//	}, 15000);
})
var _data = '';
function _getData(){
	var dateStr = $('#datepicker').val();
	$.ajax({
		url:'/home/d/parking_data/inOut?dateStr='+dateStr,
		type:'get',
		dataType:'json',
		success:function(res){
			for(var i in res.data){
				res.data[i].changeTime = toDateTime(res.data[i].changeTime);
			}
			if(_data ==''){
				_data = new Vue({
					el:'#datas',
					data:{
						datas:res.data
					}
				})
			}else{
				_data.datas = res.data;
			}
		}
	})
}