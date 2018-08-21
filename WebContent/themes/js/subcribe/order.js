$(function() {
	_loadClass();
	_getData();
})

function _getData() {
	$.ajax({
		url : '/subcribe/d/order/list',
		dataType : 'json',
		type : 'get',
		success : function(res) {
			new Vue({
				el : 'body',
				data : {
					datas : res.data
				}
			})
		}
	})
}

function _del(_e) {
	var _id = $(_e).attr('id');
	layer.open({
		content : '您确定要删除该预约吗？',
		btn : [ '是', '不要' ],
		yes : function(index) {
			layer.close(index);
			$.ajax({
				url : '/subcribe/order/del?id=' + _id,
				dataType : 'json',
				type : 'post',
				success : function(res) {
					if ('200' == res.code) {
						layer.open({
							content : '取消预约成功！',
							btn : [ '好' ],
							yes : function(index) {
									location.reload();
							}
						})
					}
				}
			})
		}
	});
}