
/**
 * 数据加载动画
 * @returns
 */
function showLoad(){  
    return layer.msg('努力加载中...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false,offset: '200px', time:100000}) ;  
}  
function closeLoad(index){  
	_i = -1;
    layer.close(index);  
} 
/**
 * 获取时间戳
 * @returns
 */
function getTimestamp(){
	return Date.parse(new Date());
}  

function getUrlParam(paraName) {
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

/**
 * 登出
 */
function loginOut(){
	$.ajax({
		url:'/paper/d/homeUser/loginOut',
		dataType:'json',
		type:'post',
		success:function(res){
			if('200'==res.code){
				layer.alert('账号退出成功！',function(){
					location.href = '/paper/p/index';
				})
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
/**
 * 时间戳转换标准时间
 * @param unixtime
 * @returns
 */
function toDate(unixtime)  {  
	 var date = new Date(unixtime);
	 return  date.Format("yyyy-MM-dd"); 
} 
/**
 * 校验正整数
 * @param str
 * @returns
 */
function isPInt(str) {
    var g = /^[1-9]*[1-9][0-9]*$/;
    return g.test(str);
}
/**
 * 校验小数
 * @param val
 * @returns
 */
function validateFloat(val){
	var patten = /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/;
	return patten.test(val);
}