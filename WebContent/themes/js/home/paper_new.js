$(function(){
	$('.submit-btn').bind('click',function(){
		_save();
	})
})
function _showUpload(){
	$('#file').click();
}
function _save(){
	var _title = $('#title').val();
	var _abstractContent = $('#abstractContent').val();
	var params = 'title='+_title+'&abstractContent='+_abstractContent+'&filePath='+_file;
	if(_file==''){
		layer.alert('You must upload a paper file!')
	}else if(_title==''||_title==null){
		layer.alert('You must input a paper’s title!')
	}else{
		$.ajax({
			url:'/paper/d/paper/save',
			data:params,
			dataType:'json',
			type:'post',
			success:function(res){
				if('200'==res.code){
					layer.alert('add success!',function(){
						location.href = '/paper/p/paper';
					})
				}else{
					layer.alert(res.msg);
				}
			}
		})
	}
	
}