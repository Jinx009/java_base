$(function(){
	_getData();
})
var _d = '';
function _getData(){
	var _mac = $('#mac').val(),_address = $('#address').val();
	$.ajax({
		url:'/common/log/sensors',
		data:'mac='+_mac+'&address='+_address,
		type:'post',
		dataType:'json',
		success:function(res){
			for(var i in res){
				res[i].createTime = toDateTime(res[i].createTime);
				res[i].lastSeenTime = toDateTime(res[i].lastSeenTime);
			}
			if(_d == ''){
				_d = new Vue({
					el:'body',
					data:{
						datas:res
					}
				})
			}else{
				_d.datas = res;
			}
			
			
		}
	})
}
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
/**
 * 时间戳转换标准时间
 * @param unixtime
 * @returns
 */
function toDateTime(unixtime)  {  
	 var date = new Date(unixtime);
	 return  date.Format("yyyy-MM-dd hh:mm:ss"); 
}  