$(function(){
	laydate.render({
	  elem: '#dateStr'
	});
	$('#dateStr').val(_newDate());
	_getData();
})
var _d = '';
function _getData(){
	var _dateStr = $('#dateStr').val();
	$.ajax({
		url:'/home/cloud/fireControl/list?dateStr='+_dateStr,
		dataType:'json',
		type:'post',
		success:function(res){
			for(var i in res.data){
				res.data[i].changeTime = toDateTime(res.data[i].changeTime);
			}
			if(_d==''){
				_d = new Vue({
					el:'#log',
					data:{
						datas:res.data
					}
				})
			}else{
				_d.datas = res.data;
			}
		}
	})
}
function _newDate(){
	var date = new Date();//获取当前时间  
	var time = date.Format("yyyy-MM-dd");  
	return time;
}

Date.prototype.Format = function (fmt) { 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}