var _menuIconList = ['fa fa-bell-slash-o','fa fa-bicycle','fa fa-binoculars','fa fa-birthday-cake','fa fa-bolt fa-bomb','fa fa-book','fa fa-bookmark','fa fa-briefcase'];
var _menuParent = new Array();
var _sonMenuList = new Array();
var _menuIconIndex = 0;
var _locationId = 14;
$(function(){
	getNav();
})
/**
 * 导航数据
 */
function getNav(){
	var _index = getSessionStorage('_index');
	var _href = getSessionStorage('_href');
	if(_index!=''){
		$('.l'+_index).addClass('active');
		$('.u'+_index).css('display','block');
		$('.u'+_index+' li').each(function(){
			var _onclick = $(this).attr('onclick');
			if(_onclick.indexOf(_href)>-1){
				$(this).addClass('active');
			}
		})
	}
	$('#warningInput').val(new Date().Format("yyyy年MM月dd日"));
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