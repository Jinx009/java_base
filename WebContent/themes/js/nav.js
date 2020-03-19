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
/**
 * 登出
 */
function loginOut(){
	$.ajax({
		url:'/home/config/loginOut',
		dataType:'json',
		type:'post',
		success:function(res){
			if('200'==res.code){
				layer.alert('账号退出成功！',function(){
					location.href = '/home/index';
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