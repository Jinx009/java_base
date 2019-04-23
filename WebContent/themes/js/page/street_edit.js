$(function(){
	_getData();
})

function _getData(){
	var _id = $('#id').text();
	$.ajax({
		url:'/d/street/find',
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
	var _parkTotal = $('#parkTotal').val();
	var _name = $('#name').val();
	var _address = $('#address').val();
	var _id = $('#id').text();
	var _params = 'streetNumber=' + _parkNumber + '&parkTotal=' + _parkTotal
			+ '&name=' + _name + '&address=' + _address+'&id='+_id;
	$.ajax({
		url : '/d/street/edit',
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


