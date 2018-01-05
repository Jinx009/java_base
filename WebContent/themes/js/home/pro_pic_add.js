function _showUpload(){
	$('#file').click();
	$('.submit-btn').bind('click',function(){
		_save();
	})
}
function _save(){
	var _name = $('#name').val();
	var _desc = $('#desc').val();
	var _picPath = $('#img').attr('src');
	var _mobilePhone = $('#mobilePhone').val();
	var _autor = $('#autor').val();
	var _params = 'name='+_name+'&desc='+_desc+'&picPath='+_picPath+'&mobilePhone='+_mobilePhone+'&autor='+_autor;
	if(_name==''||_desc==''||_picPath==''||_mobilePhone==''||_autor==''){
		layer.alert('请完善每一项选项！');
	}else{
		$.ajax({
			url:'/home/d/pro_pic/add',
			type:'post',
			data:_params,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					layer.alert('添加成功！',function(){
						_open('l4','/home/p/pro_pic');
					})
				}else{
					layer.alert(res.msg);
				}
			}
		})
	}
}