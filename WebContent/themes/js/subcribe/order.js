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

function _notice(_e,_type) {
	var _id = $(_e).attr('_id');
	$.ajax({
		url : '/subcribe/d/order/notice?id=' + _id+'&type='+_type,
		dataType : 'json',
		type : 'post',
		success : function(res) {
			if ('200' == res.code&&1==_type) {
				layer.open({
					content : '提醒已打开，我们会在开始时间两小时内通知您！',
					btn : [ '好' ],
					yes : function(index) {
						location.reload();
					}
				})
			}
			if ('200' == res.code&&0==_type) {
				layer.open({
					content : '取消提醒成功！',
					btn : [ '好' ],
					yes : function(index) {
						location.reload();
					}
				})
			}
		}
	})
}

function _del(_e) {
	var _id = $(_e).attr('id');
	layer.open({
		content : '您确定要取消该预约吗？',
		btn : [ '是', '不要' ],
		yes : function(index) {
			layer.close(index);
			$.ajax({
				url : '/subcribe/d/order/del?id=' + _id,
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