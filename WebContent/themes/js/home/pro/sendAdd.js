$(function(){
	$('.submit-btn').bind('click',function(){
		_save();
	})
})
function _save(){
	var _content = $('#content').val(),
		_name = $('#name').val();
	if(_name==''||_name==null){
		$('#errorMsg').html('推送名称不能为空！');
	}else if(_content==null||''==_content){
		$('#errorMsg').html('推送内容不能为空！');
	}else{
		var _params = 'name='+_name+'&content='+_content;
		$.ajax({
			url:'/home/d/saveNotice',
			data:_params,
			dataType:'json',
			type:'post',
			success:function(res){
				if('200'==res.code){
					layer.alert('保存成功！',function(){
						_open('7','/home/p/send');
					})
				}
			}
		})
	}
}