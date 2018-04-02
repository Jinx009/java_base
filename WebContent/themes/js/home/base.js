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

function _openC(_class,_url){
	setSessionStorage('_class',_class);
	location.href = _url;
}

/**
 * 导航数据
 */
function getNav(){
	var _class = getSessionStorage('_class');
	$('.treeview').each(function(){
		$(this).removeClass('active');
	})
	$('.'+_class).addClass('active');
	$('#warningInput').val(new Date().Format("yyyy年MM月dd日"));
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