$(function(){
	_getData();
	var _pm = getSessionStorage('_pm');
	if(_pm!=null&&_pm!=''){
		$('#pm').html('最近检测结果：'+_pm);
	}
})
var $sliderTrack = $('#sliderTrack'), $sliderHandler = $('#sliderHandler'), $sliderValue = $('#sliderValue');
function _getData(){
	$.ajax({
		url:'/interface/product/set/light',
		type:'get',
		dataType:'json',
		success:function(res){
			if('success'==res){
				layer.open({
				    type: 2
				    ,content: '等待数据上报...'
				 });
				setTimeout('_getLight();',30000);
			}else{
				var _data = '设备不在线！';
			    layer.open({
				    content: _data
				    ,btn: '好'
				});
			}
		}
	})
}

function _getLight(){
	$.ajax({
		url:'/interface/product/get',
		type:'get',
		dataType:'json',
		success:function(res){
			layer.closeAll();
			var _data = JSON.parse(res.data);
			var _arr = _data.rawData.split('');
			var _v = parseInt(_arr[3]+_arr[4]+_arr[5]+_arr[6]);
			var _a = parseInt(_arr[10]+_arr[11]+_arr[12]+_arr[13]);
			var _w = parseInt(_arr[17]+_arr[18]+_arr[19]+_arr[20]);
			var _l = parseInt(_arr[23]+_arr[24]+_arr[25]);
			$('#lightStatus').html('当前电压：'+_v+'V；当前电流：'+_a+'A；当前功率：'+_w+'W'+'；当前亮度：'+_l+'%；');
			if(_v==0){
				$sliderTrack.css('width','0%');
				$sliderHandler.css('left','0%');
				$('#lightBtn').html('当前灯具关闭').attr('onclick',function(){}).addClass('weui-btn_disabled');
				_noTouch();
			}else{
				$sliderTrack.css('width',_l+'%');
				$sliderHandler.css('left',_l+'%');
				$('#lightBtn').html('设置亮度').attr('onclick',function(){
					_setData();
				}).removeClass('weui-btn_disabled');
				_hasTouch();
			}
		}
	})
}
function _setOpenOrClose(){
	if($('#check').attr('checked') == 'checked'||$('#check').prop('checked')){
		_setStatus = '0001';
		_setData();
	}else{
		_setStatus = '0000';
		_setData();
	}
}
var _setStatus;
function _setData(){
	$.ajax({
		url:'/interface/product/set?status='+_setStatus,
		type:'get',
		dataType:'json',
		success:function(res){
			if('success'==res){
				layer.open({
				    type: 2
				    ,content: '等待数据上报...'
				 });
				setTimeout('_getData();',1000);
			}else{
				var _data = '设备不在线！';
			    layer.open({
				    content: _data
				    ,btn: '好'
				});
				$('#check').prop('checked',false); 
				$('#check').attr('checked',false); 
			}
		}
	})
}
function _hasTouch(){
	var totalLen = $('#sliderInner').width(), startLeft = 0, startX = 0;
	$sliderHandler.on(
					'touchstart',
					function(e) {
						startLeft = parseInt($sliderHandler.css('left'))* totalLen / 100;
						startX = e.changedTouches[0].clientX;
					}).on(
					'touchmove',
					function(e) {
						var dist = startLeft+ e.changedTouches[0].clientX - startX, percent;
						dist = dist < 0 ? 0 : dist > totalLen ? totalLen : dist;
						percent = parseInt(dist / totalLen * 100);
						$sliderTrack.css('width', percent + '%');
						$sliderHandler.css('left', percent + '%');
						_setStatus = parseInt(percent/10);
						_setStatus = '000'+_setStatus;
						if(_setStatus==10){
							_setStatus = '000A';
						}
						$sliderValue.text(percent);
						e.preventDefault();
					});
}
function _noTouch(){
	var totalLen = $('#sliderInner').width(), startLeft = 0, startX = 0;
	$sliderHandler.on(
					'touchstart',
					function(e) {
						console.log('_noTouch');
					}).on(
					'touchmove',
					function(e) {});
}

function _setPM2_5(){
	$('#pmBtn').hide();
	$('#pm_Btn').show();
	$.ajax({
		url:'/interface/product//set/PM2_5',
		type:'get',
		dataType:'json',
		success:function(res){
			if('success'==res){
				setTimeout('_getPMData();',30000);
			}else{
				var _data = '设备不在线！';
			    layer.open({
				    content: _data
				    ,btn: '好'
				});
			    $('#pmBtn').show();
				$('#pm_Btn').hide();
			}
		}
	})
}
function _getPMData(){
	$.ajax({
		url:'/interface/product/get',
		type:'get',
		dataType:'json',
		success:function(res){
			layer.closeAll();
			$('#pmBtn').show();
			$('#pm_Btn').hide();
			var _data = JSON.parse(res.data).rawData;
			$('#pm').html('最近检测结果：'+_data);
			setSessionStorage('_pm',_data);
		}
	})
}

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