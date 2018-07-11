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
 * 打开一个链接
 * @param _class
 * @param _url
 */
function _open(_class,_url){
	setSessionStorage('_class',_class);
	location.href = _url;
}
/**
 * 载入底部导航样式
 */
function _loadClass(){
	var _class = getSessionStorage('_class');
	$('.tab-item').each(function(){
		$(this).addClass('external');
		$(this).removeClass('active');
	})
	$('.'+_class).addClass('active');
	$('.'+_class).removeClass('external');
}
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
	if(window.sessionStorage){     
		return window.localStorage.getItem(_key);
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
