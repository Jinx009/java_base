$(function() {
	_getData();
})
function _getData() {
	$.ajax({
		url : '/d/baseLogData',
		dataType : 'json',
		type : 'post',
		success : function(res) {
			for ( var i in res.data) {
				res.data[i].createTime = toDateTime(res.data[i].createTime);
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