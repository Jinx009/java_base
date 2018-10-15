$(function(){
	var _a = getSessionStorage('_now_detail_app');
	_getData(_a);
})
function _getData(_a){
	var _url = '/interface/ja/device';
	if('pr'==_a){
		_url = '/interface/puruan/device';
	}
	$.ajax({
		url:_url,
		type:'get',
		dataType:'json',
		success:function(res){
			new Vue({
				el:'.content',
				data:{
					datas:res.data.result
				}
			})
		}
	})
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
	