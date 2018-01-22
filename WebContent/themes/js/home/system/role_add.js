function _save(){
	var name = $('#name').val(),
		description = $('#description').val();
	if(name==null||name==''||
	   description==null||description==''){
		layer.alert('请完善资料！');
	}else{
		var params = 'name='+name+'&description='+description+'&level=2';
		$.ajax({
			url:'/home/d/role_add',
			data:params,
			dataType:'json',
			type:'post',
			success:function(res){
				if('200'==res.code){
					layer.alert('操作成功！',function(){
						_open('6','/home/p/system/role');
					})
				}
			}
		})
	}
}