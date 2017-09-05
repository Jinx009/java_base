$(function(){
	 $('.form_datetime').datetimepicker({
		 format: 'YYYY-MM-DD HH:mm'
     });
	 $("#datepicker").val(new Date().Format("yyyy-MM-dd hh:mm"));
	 $("#datepicker1").val(new Date().Format("yyyy-MM-dd hh:mm"));
	 $("#datepicker2").val(new Date().Format("yyyy-MM-dd hh:mm"));
})