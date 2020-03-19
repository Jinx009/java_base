$(function() {
	_getData();
})
function _getData() {
	$.ajax({
		url : '/d/logData',
		dataType : 'json',
		type : 'post',
		success : function(res) {
			for ( var i in res.data.list) {
				res.data.list[i].createTime = toDateTime(res.data.list[i].createTime);
			}
			new Vue({
				el : '#data',
				data : {
					datas : res.data
				}
			})
		}
	})
}