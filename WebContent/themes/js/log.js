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
				res.data.list[i].enuX = res.data.list[i].enuX;
				res.data.list[i].enuY = res.data.list[i].enuY;
				res.data.list[i].enuZ =res.data.list[i].enuZ;
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