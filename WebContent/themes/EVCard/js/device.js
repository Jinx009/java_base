$(function() {
	$.ajax({
		url : '/interface/evcard/device',
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
