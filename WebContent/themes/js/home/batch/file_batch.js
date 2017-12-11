$(function(){
	_getData();
})
function _getData(){
	$.ajax({
		url:'/d/update_file/list/1_0',
			type:'post',
			dataType:'json',
			success:function(res){
				new Vue({
					el:'#files',
					data:{
						datas:res.data
					}
				})
			}
	})
}

function _showUpload(){
	$('#file').click();
}
function _delete(_e){
	var _name = $(_e).attr('id');
	var _data = {};
	_data.fileName = _name;
	$.ajax({
		url : '/d/update_file/delete/1_0',
		dataType : 'json',
		data:JSON.stringify(_data),
		contentType : 'application/json;charSet=utf8',
		type : 'post',
		success : function(res) {
			if('200'==res.code){
				layer.alert('删除成功！',function(){
					location.reload();
				})
			}else{
				layer.alert(res.msg);
			}
		}
	})
}