$(function(){
	var map = new BMap.Map("pr_map");
	var point = new BMap.Point(121.613359,31.208858);
	map.centerAndZoom(point, 19);
	var marker = new BMap.Marker(point);  
	map.addOverlay(marker);    
	var map1 = new BMap.Map("ja_map");
	var point1 = new BMap.Point(121.455187,31.242812);
	map1.centerAndZoom(point1, 22);
	var marker1 = new BMap.Marker(point1);  
	map1.addOverlay(marker1);   
	
	_getPuruanData();
	_getJaData();
})

function goD(_t){
	setSessionStorage('_now_detail_app',_t);
	location.href = '/themes/park/detail.html';
}

function _getPuruanData(){
	$.ajax({
		url:'/interface/puruan/device',
		type:'post',
		dataType:'json',
		success:function(res){
			$('#pr_total').html(res.data.result.length)
			var _now = 0;
			for(var i in res.data.result){
				if(res.data.result[i].available==0){
					_now++;
				}
			}
			$('#pr_now').html('余：'+_now);
		}
	})
}

function _getJaData(){
	$.ajax({
		url:'/interface/ja/device',
		type:'post',
		dataType:'json',
		success:function(res){
			$('#ja_total').html(res.data.result.length)
			var _now = 0;
			for(var i in res.data.result){
				if(res.data.result[i].available==0){
					_now++;
				}
			}
			$('#ja_now').html('余：'+_now);
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
	