$(function() {
	_getData();
})
function _getData() {
	$.ajax({
		url : '/home/d/user',
		type : 'post',
		dataType : 'json',
		success : function(res) {
			if ('200' == res.code) {
				new Vue({
					el : '#data',
					data : {
						datas : res.data
					}
				})
			}
		}
	})
}
function _delete(_e) {
	var _id = $(_e).attr('id');
	layer.confirm('您确定删除该用户？', {
		btn : [ '否', '是' ]
	}, function() {
		layer.closeAll();
	}, function() {
		$.ajax({
			url:'/home/d/user_delete',
			type:'post',
			data:'id='+_id,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					layer.laert('操作成功！',function(){
						location.reload();
					})
				}
			}
		})
	});
}

function _edit(_e){
	var _id = $(_e).attr('id');
	var _roleName = $(_e).attr('roleName');
	var _userName = $(_e).attr('userName');
	var _realName = $(_e).attr('realName')
}