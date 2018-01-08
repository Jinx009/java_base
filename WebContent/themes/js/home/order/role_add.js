$(function() {
	_getArea();
	laydate.render({
		elem : '#time',
		type : 'time',
		range : true
	});
})

function _getArea() {
	$.ajax({
		url : '/home/d/area/list?id=26',
		type : 'get',
		dataType : 'json',
		success : function(res) {
			if (res.data != null) {
				new Vue({
					el : '#area',
					data : {
						datas : res.data
					}
				})
			}
		}
	})
}