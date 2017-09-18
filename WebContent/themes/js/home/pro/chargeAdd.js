$(function(){
	 $('.form_datetime').datetimepicker({
		 format: 'YYYY-MM-DD HH:mm'
     });
	 $("#datepicker").val(new Date().Format("yyyy-MM-dd hh:mm"));
	 $("#datepicker1").val(new Date().Format("yyyy-MM-dd hh:mm"));
	 $("#datepicker2").val(new Date().Format("yyyy-MM-dd hh:mm"));
	 _getArea();
})
var _area = {},_street = {};
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
	_street = {};
	var _areaId = $('#area').val();
	$.ajax({
		url:'/home/d/streetByAreaId',
		type:'post',
		data:'areaId='+_areaId,
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				_street = new Vue({
					el:'#street',
					data:{
						streets:res.data
					}
				})
			}
		}
	})
}