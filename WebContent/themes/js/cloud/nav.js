var _menuIconList = ['fa fa-bell-slash-o','fa fa-bicycle','fa fa-binoculars','fa fa-birthday-cake','fa fa-bolt fa-bomb','fa fa-book','fa fa-bookmark','fa fa-briefcase'];
var _menuParent = new Array();
var _sonMenuList = new Array();
var _menuIconIndex = 0;
var _locationId = 14;
$(function(){
	getNav();
})

function _getPage(_type,_index){
	var _p = -1;
	if(_type==0){
		_p =  _index;
	}
	if(_type==1&&_index==0){
		_p =   (_nowPage - 1);
	}
	if(_type==1&&_index==1){
		_p = (_nowPage + 1);
	}
	if(parseInt(_p)<=0||(_max!=0&&_p>_max)){
		return -1;
	}
	if(_p===_nowPage){
		return -1;
	}
	_nowPage = _p;
	return _p;
}

/**
 * 导航数据
 */
function getNav(){
	var _index = getSessionStorage('_index');
	if(_index==''||_index==null){
		_index = 'l1_d1';
	}
	var _l = _index.split('_')[0];
	var _d = _index.split('_')[1];
	$('li').each(function(){
		$(this).removeClass('layui-nav-itemed');
	})
	$('dd').each(function(){
		$(this).removeClass('layui-this');
	})
	$('#'+_l).addClass('layui-nav-itemed');
	$('#'+_d).addClass('layui-this');
}
/**
 * 打开菜单链接
 * @param _index
 * @param _href
 */
function _open(_index,_href){
	setSessionStorage('_index',_index);
	setSessionStorage('_href',_href);
	location.href = _href;
}
function _open_(_index,_href,_href_){
	setSessionStorage('_index',_index);
	setSessionStorage('_href',_href);
	location.href = _href_;
}
/**
 * 操作sessionStorage
 * @param _key
 */
function getSessionStorage(_key){
	if(window.sessionStorage){     
		return window.sessionStorage.getItem(_key);
	}
}
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