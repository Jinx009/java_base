/**
 * 获取storage
 * @param _key
 * @returns
 */
function getSessionStorage(_key){
	if(window.sessionStorage){     
		return window.sessionStorage.getItem(_key);
	}
	return '';
}
/**
 * 设置storage
 * @param _key
 * @param _value
 * @returns
 */
function setSessionStorage(_key,_value){
	if(window.sessionStorage){     
		var _r = window.sessionStorage.setItem(_key,_value);
		if(_r!=null&&_r!=''&&_r!=undefined){
			return _r;
		}else{
			return '';
		}
	}else{ 
		return '';
	}
}

/**
 * 获取storage
 * @param _key
 * @returns
 */
function getLocalStorage(_key){
	var _d = '';
	if(window.sessionStorage){     
		_d = window.localStorage.getItem(_key);
	}
	if(_d!=''&&_d!=null){
		return _d;
	}
	return '';
}
/**
 * 设置storage
 * @param _key
 * @param _value
 * @returns
 */
function setLocalStorage(_key,_value){
	if(window.localStorage){     
		var _r = window.localStorage.setItem(_key,_value);
		if(_r!=null&&_r!=''&&_r!=undefined){
			return _r;
		}else{
			return '';
		}
	}else{ 
		return '';
	}
}
function _newDate_1(){
	var date = new Date();//获取当前时间  
	date.setDate(date.getDate()-1);//设置天数 -1 天  
	var time = date.Format("yyyy-MM-dd");  
	return time;
}
function _newDate_2(){
	var date = new Date();//获取当前时间  
	date.setDate(date.getDate()+1);//设置天数 -1 天  
	var time = date.Format("yyyy-MM-dd");  
	return time;
}
function _newDate(){
	var date = new Date();//获取当前时间  
	var time = date.Format("yyyy-MM-dd");  
	return time;
}
//对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
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