$(function() {
	$('#datepicker').datepicker({
		autoclose : true,
		format : 'yyyy-mm-dd',
		lang : 'ch'
	});
	$('#datepicker').val(new Date().Format('yyyy-MM-dd'));
	if (!window.localStorage) {
		alert('浏览器不支持localstorage');
		return false;
	} else {
		var storage = window.localStorage;
		var _obj = {};
		_obj.status = storage.getItem('_status');
		_obj.mac = storage.getItem('_mac');
		new Vue({
			el:'#data',
			data:{
				obj:_obj
			}
		})
		_mac = _obj.mac;
		_getData();
	}
})
var _mac = '';
function _getData(){
	var _dateStr = $('#datepicker').val();
	var _arr = _dateStr.split('-');
	var _date = _arr[1]+_arr[2];
	$.ajax({
		url:'/interface/shangcheng/getLog?mac='+_mac+'&dateStr='+_date,
		dataType:'json',
		type:'get',
		success:function(res){
			$('#logs').html('<pre>'+res.data+'</pre>');
			location.href = '#_2';
		}
	})
}
Date.prototype.Format = function(fmt) { 
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};