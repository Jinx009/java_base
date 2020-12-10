$(function(){
	$('#datepicker').val(new Date().Format('yyyy-MM-dd'));
	laydate.render({
		elem : '#datepicker',
	});
//	_getData();
})
 var _d = '';
function _getData(){
	var dateStr = $('#datepicker').val();
	var mac = $('#mac').val();
	var _data = {};
	_data.mac = mac;
	_data.dateStr = dateStr;
	$('#data').hide();
	$.ajax({
		url:'/d/log_sensor/heart/1_0',
		dataType:'json',
		data:JSON.stringify(_data),
		contentType:'application/json;charSet=utf8',
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
