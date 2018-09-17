$(function() {
	_getData();
})
function _getData() {
	var _id = GetUrlParam('id');
	$.ajax({
		url : '/front/d/pro_task/detail?id=' + _id,
		type : 'get',
		dataType : 'json',
		success : function(res) {
			new Vue({
				el : 'body',
				data : {
					item : res.data
				}
			})
		}
	})
}
function GetUrlParam(paraName) {
	var url = document.location.toString();
	var arrObj = url.split("?");
	if (arrObj.length > 1) {
		var arrPara = arrObj[1].split('&');
		var arr;
		for (var i = 0; i < arrPara.length; i++) {
			arr = arrPara[i].split('=');
			if (arr != null && arr[0] == paraName) {
				return arr[1];
			}
		}
		return '';
	}
	return '';
}

function _edit() {
	var _id = GetUrlParam('id');
	$.ajax({
		url : '/front/d/pro_task/changeStatus?id=' + _id,
		type : 'post',
		dataType : 'json',
		success : function(res) {
			if ('200' == res.code) {
				layer.open({
					content : '状态更改成功！',
					btn : '好',
					yes:function(){
						location.reload();
					}
				});
			}
		}
	})
}