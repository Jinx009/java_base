function save(){
	var params = 'name='+$('#name').val()+'&email='+email+'&mobilePhone='+$('#mobilePhone').val()+'&address='+$('#address').val()+'&withNum='+$('#withNum').val();
	$.ajax({
		url:'/front/d/saveUser',
		type:'post',
		data:params,
		dataType:'json',
		success:function(res){
			  layer.open({content: '保存成功！',btn: '我知道了'});
		}
	})
}