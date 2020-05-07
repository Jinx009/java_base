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
				res.data.list[i].enuX = new Number(res.data.list[i].enuX*1000).toFixed(12);
				res.data.list[i].enuY = new Number(res.data.list[i].enuY*1000).toFixed(12);
				res.data.list[i].enuZ = new Number(res.data.list[i].enuZ*1000).toFixed(12);
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