$(function(){
	_getData();
})

function _getData(){
	var _id = $('#id').text();
	$.ajax({
		url:'/d/camera/find',
		dataType:'json',
		data:'id='+_id,
		type:'post',
		success:function(res){
			new Vue({
				el:'#data',
				data:{
					datas:res.data
				}
			})
		}
	})
}

function _edit() {
	var _parkNumber = $('#parkNumber').val();
	var _streetId = $('#streetId').val();
	var _streetName = $('#streetId option:selected"').text();
	var _deviceType = $('#deviceType').val();
	var _id = $('#id').text();
	var _params = 'parkNumber=' + _parkNumber + '&streetId=' + _streetId
			+ '&streetName=' + _streetName + '&deviceType=' + _deviceType+'&id='+_id;
	$.ajax({
		url : '/d/camera/update',
		dataType : 'json',
		type : 'post',
		data : _params,
		success : function(res) {
			if ('200' == res.code) {
				layer.alert('编辑成功！', function() {
					location.reload();
				})
			} else {
				layer.alert(res.msg);
			}
		}
	})
}


