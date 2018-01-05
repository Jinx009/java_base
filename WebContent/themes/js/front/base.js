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
