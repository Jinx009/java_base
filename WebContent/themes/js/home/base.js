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
 * 隐藏新建弹框
 */
function _hideNew(){
	$('#newBox').hide();
	$('.content').css('opacity',1);
}
/**
 * 显示新建弹框
 */
function _showNew(){
	$('#newBox').show();
	$('.content').css('opacity',0.3);
}

function _getAllPage(_e){
	var _p = $(_e).val();
	if(_p!=_nowPage&&_p>0&&_p<_max){
		_getData(0,parseInt(_p));
	}else{
		$(_e).val('当前第'+_nowPage+'页，共'+_max+'页');
	}
}
function _getNowPage(_e){
	$(_e).val('');
	$(_e).attr('placeholder',_nowPage);
}
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
		url:'/data/loginOut',
		dataType:'json',
		type:'post',
		success:function(res){
			layer.alert('账号退出成功！',function(){
				location.href = '/index';
			});
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

/**
 * 生成UUID
 * @returns {string}
 */
function uuid() {
    var s = [];
    var hexDigits = '0123456789abcdef';
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = '4';  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = '-';

    var uuid = s.join('');
    return uuid;
}