$(function(){
	$('#datepicker').val(new Date().Format('yyyy-MM-dd'));
	laydate.render({
		elem : '#datepicker',
	});
})
 var _d = '';
function _getData(){
	var dateStr = $('#datepicker').val();
	var mac = $('#mac').val();
	$('#data').hide();
	$.ajax({
		url:'/iot/iot/sensor/source',
		dataType:'json',
		data:'dateStr='+dateStr+'&mac='+mac,
		type:'post',
		success:function(res){
			if(res.data!=null&&res.data!=''){
				$('#data').show();
			}
			for(var i in res.data){
				res.data[i].createTime = toDateTime(res.data[i].createTime);
			}
			if(_d==''){
				_d = new Vue({
					el:'#data',
					data:{
						datas:res.data
					}
				})
			}else{
				_d.datas = res.data;
			}
			
		}
	})
}
