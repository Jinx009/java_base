$(function(){
	_getLight();
	var _pm = getSessionStorage('_pm');
	if(_pm!=null&&_pm!=''){
		$('#r').val(_pm);
	}
	_hasTouch();
})
var $sliderTrack = $('#sliderTrack'), $sliderHandler = $('#sliderHandler'), $sliderValue = $('#sliderValue');
function _getData(){
	$.ajax({
		url:'/interface/product/set/light',
		type:'get',
		dataType:'json',
		success:function(res){
			if(res!=null&&'success'==res.data){
				layer.open({
				    type: 2
				    ,content: '等待数据上报...'
				 });
				$('#pmBtn').html('查询').attr('onclick','_setPM2_5();').removeClass('weui-btn_disabled');
				setTimeout('_getLight();',30000);
			}else{
				var _data = JSON.parse(res.data);
			    layer.open({
				    content: _data.errorMsg
				    ,btn: '好'
				});
			    $('#status').val('OFFLINE');
			    $('#l').val('0%');
			    $('#status_1').val('OFFLINE');
				$sliderTrack.css('width','0%');
				$sliderHandler.css('left','0%');
				$('#lightBtn').html('当前设备关闭').attr('onclick','').addClass('weui-btn_disabled');
				$('#pmBtn').html('当前设备关闭').attr('onclick','').addClass('weui-btn_disabled');
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
			var _date = _data.eventTime;
			var date1 = new Date( new Date(Date.parse(_date.replace(/-/g,"/"))).getTime());
			var date2 = new Date();
			var s1 = date1.getTime(),s2 = date2.getTime();
			var total = (s2 - s1)/1000;
			if(_arr.length>14&&total<30){
				var _v = parseInt(_arr[3]+_arr[4]+_arr[5]+_arr[6]);
				var _a = parseInt(_arr[10]+_arr[11]+_arr[12]+_arr[13]);
				var _w = parseInt(_arr[17]+_arr[18]+_arr[19]+_arr[20]);
				var _l = parseInt(_arr[23]+_arr[24]+_arr[25]);
				$('#status').val('ONLINE');
			    $('#status_1').val('ONLINE');
			    $('#v').val(_v+'V');
			    $('#a').val(_a+'A');
			    $('#w').val(_w+'W');
			    $('#l').val(_l+'%');
				$sliderTrack.css('width',_l+'%');
				$sliderHandler.css('left',_l+'%');
				$('#lightBtn').html('设置亮度'+_l+'%').attr('onclick','_setData();').removeClass('weui-btn_disabled');
				_hasTouch();
			}else{
				_getData();
			}
		}
	})
}
var _setStatus ='0001';
function _setData(){
	$.ajax({
		url:'/interface/product/set?status='+_setStatus,
		type:'get',
		dataType:'json',
		success:function(res){
			if(res!=null&&'success'==res.data){
				if(_setStatus=='000A'){
					 $('#l').val('100%');
				}else{
					$('#l').val(parseInt(_setStatus)*10+'%');
				}
			}else{
				var _data = JSON.parse(res.data);
			    layer.open({
				    content: _data.errorMsg
				    ,btn: '好'
				});
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
						if(_setStatus==0){
							$('#lightBtn').html('关灯').attr('onclick','_setData();').removeClass('weui-btn_disabled');
						}else{
							$('#lightBtn').html('设置亮度'+_setStatus*10+'%').attr('onclick','_setData();').removeClass('weui-btn_disabled');
						}
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
			if(res!=null&&'success'==res.data){
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
			$('#r').val(_data);
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