$(function() {
	$.ajax({
		url : '/interface/bearhunting/device',
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


function goLog(_element){
	if(!window.localStorage){
        alert('浏览器不支持localstorage');
        return false;
    }else{
        var storage = window.localStorage;
        storage['_mac'] = $(_element).html();
        location.href = '/themes/bearhunting/logs.html';
    }
}