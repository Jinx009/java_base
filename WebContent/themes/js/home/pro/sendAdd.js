$(function(){
	_id = $('#_id').val();
	$('.submit-btn').bind('click',function(){
		_update();
	})
	_getData();
})
var _id;
function _update(){
	var _params = 'id='+_id+'&name='+$('#name').val()+'&status='+$('#status').val()+
	'&startTime='+$('#startTime').val()+'&endTime='+$('#endTime').val()+'&content='+$('#content').val();
	$.ajax({
		url:'/home/d/noticeUpdate',
		type:'post',
		data:_params,
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				layer.alert('操作成功!',function(){
					_open('7','/home/p/send');
				})
			}
		}
	})
}
function _getData(){
	if('0'!=_id){
		$.ajax({
			url:'/home/d/singleNotice?id='+_id,
			type:'get',
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					$('#name').val(res.data.name);
					$('#content').val(res.data.content);
					$('#startTime').val(res.data.startTime);
					$('#endTime').val(res.data.endTime);
					var _htmlStr = '<option value="1" >正常推送</option><option value="2" >活动推送</option>';
					if('2'==res.data.status){
						_htmlStr = '<option value="2" >活动推送</option><option value="1" >正常推送</option>';
					}
					$('#status').html(_htmlStr);
				}
			}
		})
	}
}