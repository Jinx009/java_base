$(function() {
	$.ajax({
		url : '/interface/boken/device',
		type : 'get',
		dataType : 'json',
		success : function(res) {
			new Vue({
				el : '#data',
				data : {
					datas : res.data.result
				}
			})
		}
	})
})
function goJob(_element) {
	var _target = $(_element).attr('target');
	var _mac = $(_element).attr('mac');
	layer.confirm('您确定要校准' + _mac + '吗？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : '/common/job/find?serviceName=boken',
			type : 'get',
			dataType : 'json',
			success : function(res) {
				if ('200' == res.code) {
					layer.alert('尚有未完成任务！');
				} else {
					$.ajax({
						url : '/common/job/save/cfgsensor',
						type : 'post',
						data : 'target=' + _target + '&mac=' + _mac
								+ '&serviceName=boken',
						dataType : 'json',
						success : function(resp) {
							if ('200' == resp.code) {
								layer.alert('正在校准中...', function() {
									location.href = '/themes/boken/job.html';
								})
							} else {
								layer.alert(resp.msg);
							}
						}
					})
				}
			}
		})
	}, function() {
		layer.closeAll();
	});
}