$(function(){
	_getData();
})

function _getData(){
	$.ajax({
		url:'/d/vedioArea/list',
		type:'get',
		dataType:'json',
		success:function(res){
			for(var i in res.data){
				res.data[i].createTime = jsDateTime(res.data[i].createTime);
			}
			new Vue({
				el:'#datas',
				data:{
					datas:res.data
				}
			})
		}
	})
}
function jsDateTime(unixtime) {
	var date = new Date(unixtime);
	return date.Format("yyyy-MM-dd hh:mm:ss");
}
Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

function _goDetail(_e){
	console.log($(_e).attr('id'))
	var _id = $(_e).attr('id').split('_info')[1];
	setSessionStorage('_detailId',_id);
	location.href = '/themes/vedio/area_detail.html';
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

function _del(_e){
	var _id = $(_e).attr('id').split('_del')[1];
	$.ajax({
		url:'/d/vedioArea/del',
		data:"id="+_id,
		dataType:'json',
		success:function(res){
			layer.alet('删除成功',function(){
				location.reload();
			})
		}
	})
}