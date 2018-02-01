$(function() {
	$.ajax({
		url : '/interface/xj/device',
		type : 'get',
		dataType : 'json',
		success : function(res) {
			for ( var i in res.data.result) {
				var _sensor = res.data.result[i];
				if ('' != _sensor.bluetooth && _sensor.bluetooth != null) {
					$.ajax({
						url : '/common/bluetooth/findByUuid?uuid='
								+ _sensor.bluetooth,
						type : 'get',
						data : 'json',
						async : false,
						success : function(resp) {
							if (resp.data != null) {
								res.data.result[i].uid = resp.data.carNo;
							}
						}
					})
				}
			}
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
			url : '/common/job/find?serviceName=xj',
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
								+ '&serviceName=xj',
						dataType : 'json',
						success : function(resp) {
							if ('200' == resp.code) {
								layer.alert('正在校准中...', function() {
									location.href = '/themes/xj/job.html';
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
function goLog(_element){
	if(!window.localStorage){
        alert('浏览器不支持localstorage');
        return false;
    }else{
        var storage = window.localStorage;
        storage['_mac'] = $(_element).html();
        location.href = '/themes/xj/logs.html';
    }
}