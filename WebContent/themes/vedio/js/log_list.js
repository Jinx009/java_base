$(function(){
	_taskDetailId = getSessionStorage('taskDetailId');
	_getData();
	
})
var _taskDetailId;
function _getData(){
	$.ajax({
		url:'/d/vedioLog/findByTaskId?id='+_taskDetailId,
		type:'get',
		dataType:'json',
		success:function(res){
			new Vue({
				el:'#datas',
				data:{
					datas:res.data
				}
			})
		}
	})
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

function _goDetail(_e){
	var _id = $(_e).attr('id');
	setSessionStorage('logDetailId',_id);
	location.href = '/themes/vedio/log_detail.html';
}

function getSessionStorage(_key){
	if(window.sessionStorage){     
		return window.sessionStorage.getItem(_key);
	}
}