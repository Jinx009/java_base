function _showUpload(){
	$('#file').click();
}
function _save(){
	var _name = $('#name').val(),
		_appId = $('#appId').val(),
		_appSecret = $('#appSecret').val(),
		_logoPath = $('#img').attr('src'),
		_merchantNum = $('#merchantNum').val();
	if(_logoPath==null||''==_logoPath){
		$('#errorMsg').html('标识图片未正确上传!')
	}else if(_name==null||''==_name){
		$('#errorMsg').html('渠道名称未填写!');
	}else{
		var _params = 'name='+_name+'&logoPath='+_logoPath+'&appId='+_appId+'&appSecret='+_appSecret+'&merchantNum='+_merchantNum;
		$.ajax({
			url:'/home/d/savePay',
			type:'post',
			dataType:'json',
			data:_params,
			success:function(res){
				if('200'==res.code){
					layer.alert('新建成功！',function(){
						_open('7','/home/p/pay');
					})
				}else{
					layer.alert(res.msg);
				}
			}
		})
	}
}