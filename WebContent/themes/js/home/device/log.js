$(function() {
	_getData();
})

function _getData() {
	$.ajax({
		url : '/d/device_log/list/1_0',
		dataType : 'json',
		contentType : 'application/json;charSet=utf8',
		type : 'post',
		success : function(res) {
			res.data.sort(function(x, y){
				  return x[4] - y[4];
			});
			for ( var i in res.data) {
				res.data[i].lastModifiedTime = toDateTime(res.data[i].lastModifiedTime);
			}
			var _d = new Vue({
				el : '#datas',
				data : {
					datas : res.data
				}
			})
		}
	})
}

function _down(_e){
	var _fileName = $(_e).attr('id').split('_d')[1];
	window.open('/data/download/log?fileName='+_fileName,'_self');
}

function _delete(_e){
	var _fileName = $(_e).attr('id').split('_j')[1];
	var _data = {};
	_data.fileName = _fileName;
	$.ajax({
		url : '/d/device_log/delete/1_0',
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

function _serach(){
	var _fileName = $('#fileName').val();
	$('#datas b').each(function(){
		$(this).css('color','black');
		var _id = $(this).attr('id');
		console.log(_id)
		if(_id.indexOf(_fileName) > -1){
			location.href = '#'+_id;
			$(this).css('color','red');
		}
	})
}