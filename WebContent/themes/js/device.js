$(function() {
	_getData();
})
function _getData() {
	$.ajax({
		url : '/d/deviceData',
		dataType : 'json',
		type : 'post',
		success : function(res) {
			for ( var i in res.data) {
				res.data[i].createTime = toDateTime(res.data[i].createTime);
				res.data[i].updateTime = toDateTime(res.data[i].updateTime);
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