function save(){
	var _params = 'mac='+$('#mac').val()+'&code='+$('#code').val()+'&place='+$('#place').val()+'&remark='+$('#remark').val();
	$.ajax({
		url:'/home/d/insert',
		type:'post',
		dataType:'json',
		data:_params,
		success:function(res){
			console.log(res);
		}
	})
}