$(function(){
	_getData();
})
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

function _getData(){
	var _id = GetUrlParam('id');
	$.ajax({
		url:'/paper/d/result/list?id='+_id,
		type:'post',
		dataType:'json',
		success:function(res){
			new Vue({
				el:'body',
				data:{
					datas:res.data
				}
			})
		}
	})
}