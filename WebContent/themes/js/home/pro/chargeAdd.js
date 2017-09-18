$(function(){
	$('#datepicker').datepicker({
      autoclose: true,
      format: 'yyyy-mm-dd',
      lang: 'ch'
    });
	$("#datepicker").val(new Date().Format("yyyy-MM-dd"));
	 _getArea();
	 var _timeHtmlStr = '';
	 for(var i = 0;i<25;i++){
		 _timeHtmlStr += '<option value="'+i+'" >'+i+'时</option>';
	 }
	 $('#startTime').html(_timeHtmlStr);
	 $('#endTime').html(_timeHtmlStr);
	 $('.submit-btn').bind('click',function(){
		 _save();
	 })
})
var _area = {},_street = null;
function _getArea(){
	$.ajax({
		url:'/home/d/area',
		type:'get',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				_area = new Vue({
					el:'#area',
					data:{
						areas:res.data
					}
				})
				_getStreet();
			}
		}
	})
}
function _getStreet(){
	var _areaId = $('#area').val();
	$.ajax({
		url:'/home/d/streetByAreaId',
		type:'post',
		data:'areaId='+_areaId,
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				for(var i in res.data){
					
				}
				if(_street!=null){
					_street.streets = res.data;
				}else{
					_street = new Vue({
						el:'#street',
						data:{
							streets:res.data
						}
					})
				}
			}
		}
	})
}

function _save(){
	var dateType = $('#dateType').val(),
		singleDate = $('#datepicker').val(),
		startTime = $('#startTime').val(),
		endTime = $('#endTime').val(),
		price = $('#price').val(),
		freeTime = $('#freeTime').val();
		if(!isPInt(freeTime)&&freeTime!=0){
			$('#errorMsg').html('免停时间必须为正整数！');
		}else if(!validateFloat(price)&&!isPInt(price)){
			$('#errorMsg').html('单价格式不正确！');
		}else{
			var _params = '_singleDate='+singleDate+'&startTime='+startTime+'&endTime='+endTime+
			'&freeTime='+freeTime+'&price='+price+'&dateType='+dateType+'&areaId='+$('#area').val()+'&streetId='+$('#street').val();
			$.ajax({
				url:'/home/d/saveCharge',
				data:_params,
				dataType:'json',
				type:'post',
				success:function(res){
					if('200'==res.code){
						layer.alert('创建成功！',function(){
							_open('7','/home/p/charge');
						})
					}else{
						layer.alert(res.msg);
					}
				}
			})
		}
}